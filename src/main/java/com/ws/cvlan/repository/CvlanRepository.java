package com.ws.cvlan.repository;

import com.ws.cvlan.enums.*;
import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.cvlan.filter.RemoveCvlanBlockFilter;
import com.ws.cvlan.filter.validation.BaseCvlanFilter;
import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import com.ws.cvlan.pojo.DTOs.CreateMessageCheckCvlanBlockExistsDTO;
import com.ws.cvlan.pojo.response.AddCvlanBlockResponse;
import com.ws.cvlan.pojo.response.ListCvlanBlockResponse;
import com.ws.cvlan.pojo.response.RemoveCvlanBlockResponse;
import com.ws.cvlan.sql.CVLAN.*;
import com.ws.utils.enums.Operation;
import com.ws.utils.enums.OperationResult;
import com.ws.utils.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ws.utils.StringUtilSol.getLong;
import static com.ws.utils.StringUtilSol.getString;

@Repository
public class CvlanRepository {

    private static final Logger logger = LoggerFactory.getLogger(CvlanRepository.class);
    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private AuditoriaLogRepository auditoriaLog;

    public CvlanRepository() {
    }

    public boolean serviceExistByCvlan(BaseCvlanFilter baseCvlanFilter) {
        String query = FindServiceByCvlanSql.getQueryFindServiceByCvlan(baseCvlanFilter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return !resultTuples.isEmpty() && !getString(resultTuples.get(0), CvlanExistStructureAttr.NAME).isEmpty();
    }

    public CreateMessageCheckCvlanBlockExistsDTO checkCvlanBlockExistsForBlock(BaseCvlanFilter baseCvlanFilter) {
        CheckCvlanBlockExistsDTO checkCvlanBlockExistsDTO = new CheckCvlanBlockExistsDTO(baseCvlanFilter);

        String query = CheckCvlanBlockExistsSql.getQueryCheckCvlanBlockExists(checkCvlanBlockExistsDTO, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);

        if (!resultTuples.isEmpty()) {
            Long id = getLong(resultTuples.get(0), CheckCvlanBlockExistsAttr.PROCESS_ID);
            if (id != null && id != 0L) {
                return new CreateMessageCheckCvlanBlockExistsDTO(resultTuples);
            }
        }
        return new CreateMessageCheckCvlanBlockExistsDTO(Collections.emptyList());
    }


    public CreateMessageCheckCvlanBlockExistsDTO checkCvlanBlockExistsForUnblock(BaseCvlanFilter baseCvlanFilter) {
        CheckCvlanBlockExistsDTO checkCvlanBlockExistsDTO = new CheckCvlanBlockExistsDTO(baseCvlanFilter);
        String query = CheckCvlanBlockExistsSql.getQueryCheckCvlanBlockExistsForUnblock(checkCvlanBlockExistsDTO, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);

        if (!resultTuples.isEmpty()) {
            Long id = getLong(resultTuples.get(0), CheckCvlanBlockExistsAttr.PROCESS_ID);
            if (id != null && id != 0L) {
                return new CreateMessageCheckCvlanBlockExistsDTO(resultTuples);
            }
        }
        return new CreateMessageCheckCvlanBlockExistsDTO(Collections.emptyList());
    }



    @Transactional
    public AddCvlanBlockResponse addCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter) {
        logger.info("[API-MIGRABLOCK-LOG] Starting addCvlanBlock operation for filter: {}", addCvlanBlockFilter);
        try {
            if (!serviceExistByCvlan(addCvlanBlockFilter)) {
                logger.warn("[API-MIGRABLOCK-LOG] CVLAN not found for filter: {}", addCvlanBlockFilter);
                return createAddCvlanBlockResponse(OperationResult.CVLAN_NOT_FOUND, null);
            }

            CreateMessageCheckCvlanBlockExistsDTO cvlanAlreadyBlocked = checkCvlanBlockExistsForBlock(addCvlanBlockFilter);
            if (cvlanAlreadyBlocked.isExist()) {
                if (isBlocked(cvlanAlreadyBlocked)) {
                    logger.info("[API-MIGRABLOCK-LOG] CVLAN already blocked for filter: {}", addCvlanBlockFilter);
                    AddCvlanBlockResponse response = createAddCvlanBlockResponse(OperationResult.CVLAN_BLOCKED, cvlanAlreadyBlocked.getProcessId());
                    response.setMessage(cvlanAlreadyBlocked.getMessage());
                    return response;
                } else {
                    logger.info("[API-MIGRABLOCK-LOG] Updating CVLAN block for filter: {}", addCvlanBlockFilter);
                    return executeCvlanBlockUpdate(addCvlanBlockFilter, cvlanAlreadyBlocked.getProcessId());
                }
            }
            logger.info("[API-MIGRABLOCK-LOG] Inserting new CVLAN block for filter: {}", addCvlanBlockFilter);
            return executeCvlanBlockInsertion(addCvlanBlockFilter);
        } catch (Exception e) {
            logger.error("[API-MIGRABLOCK-LOG] Error occurred during addCvlanBlock operation", e);
            AddCvlanBlockResponse response = createAddCvlanBlockResponse(OperationResult.UNKNOWN_ERROR, null);
            response.setStackTrace(e.getMessage());
            return response;
        } finally {
            logger.info("[API-MIGRABLOCK-LOG] Completed addCvlanBlock operation for filter: {}", addCvlanBlockFilter);
        }
    }

    private boolean isBlocked(CreateMessageCheckCvlanBlockExistsDTO cvlanAlreadyBlocked) {
        return cvlanAlreadyBlocked.getStatusCVLAN() == StatusCVLAN.BLOCKED.getValue();
    }

    private AddCvlanBlockResponse executeCvlanBlockInsertion(AddCvlanBlockFilter addCvlanBlockFilter) {
        String query = AddCvlanBlockSql.getQueryAddCvlanBlock(addCvlanBlockFilter, sqlParameterSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, sqlParameterSource, keyHolder, new String[]{"ID"});
        Long processId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        query = UpdateObservationCvlanSql.getUpdateObservationQuery(addCvlanBlockFilter.getOltUid(), addCvlanBlockFilter.getBlockReason(), sqlParameterSource);
        jdbcTemplate.update(query, sqlParameterSource);

        auditoriaLog.insertAuditLog(
                addCvlanBlockFilter.getLogin(),
                addCvlanBlockFilter.getSystemOrigin(),
                Operation.BLOCK_CVLAN,
                addCvlanBlockFilter.getBlockReason(),
                processId
        );
        return createAddCvlanBlockResponse(OperationResult.SUCCESS, processId);
    }

    private AddCvlanBlockResponse executeCvlanBlockUpdate(AddCvlanBlockFilter addCvlanBlockFilter, Long idBlock) {
        String query = UpdateCvlanBlockSql.getQueryUpdateCvlanBlock(idBlock, sqlParameterSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, sqlParameterSource, keyHolder, new String[]{"ID"});
        Long processId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        auditoriaLog.insertAuditLog(
                addCvlanBlockFilter.getLogin(),
                addCvlanBlockFilter.getSystemOrigin(),
                Operation.BLOCK_CVLAN,
                addCvlanBlockFilter.getBlockReason(),
                processId
        );
        return createAddCvlanBlockResponse(OperationResult.SUCCESS, processId);
    }

    private AddCvlanBlockResponse createAddCvlanBlockResponse(OperationResult operationResult, Long idCvlan) {
        AddCvlanBlockResponse response = new AddCvlanBlockResponse();
        response.setOperationResult(operationResult);

        if (Status.SUCCESS.equals(operationResult.getStatus())) {
            response.setId(idCvlan);
        }
        return response;
    }

    public ListCvlanBlockResponse getCvlanBlockList(ListCvlanBlockFilter filter) {
        logger.info("[API-MIGRABLOCK-LOG] Starting getCvlanBlockList operation for filter: {}", filter);
        String query = ListCvlanBlocksSql.getQueryListCvlanBlock(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        ListCvlanBlockResponse response = new ListCvlanBlockResponse(resultTuples);
        logger.info("[API-MIGRABLOCK-LOG] Completed getCvlanBlockList operation for filter: {}", filter);
        return response;
    }

    @Transactional
    public RemoveCvlanBlockResponse executeCvlanBlockRemove(RemoveCvlanBlockFilter filter) {
        logger.info("[API-MIGRABLOCK-LOG] Starting executeCvlanBlockRemove operation for filter: {}", filter);
        try {
            CreateMessageCheckCvlanBlockExistsDTO cvlanAlreadyBlocked = checkCvlanBlockExistsForUnblock(filter);
            if (cvlanAlreadyBlocked.isExist() && isBlocked(cvlanAlreadyBlocked)) {
                String query = RemoveCvlanBlocksSql.getQueryRemoveCvlanBlock(cvlanAlreadyBlocked.getProcessId(), sqlParameterSource);

                jdbcTemplate.update(query, sqlParameterSource);

                query = UpdateObservationCvlanSql.getUpdateObservationQuery(filter.getOltUid(), filter.getRemoveBlockReason(), sqlParameterSource);
                jdbcTemplate.update(query, sqlParameterSource);

                auditoriaLog.insertAuditLog(
                        filter.getLogin(),
                        filter.getSystemOrigin(),
                        Operation.UNBLOCK_CVLAN,
                        filter.getRemoveBlockReason(),
                        cvlanAlreadyBlocked.getProcessId()
                );

                return createRemoveCvlanBlockResponse(OperationResult.SUCCESS, cvlanAlreadyBlocked.getProcessId());
            }

            return createRemoveCvlanBlockResponse(OperationResult.BLOCK_NOT_FOUND, null);
        } catch (Exception e) {
            logger.error("[API-MIGRABLOCK-LOG] Error occurred during executeCvlanBlockRemove operation", e);
            RemoveCvlanBlockResponse response = createRemoveCvlanBlockResponse(OperationResult.UNKNOWN_ERROR, null);
            response.setStackTrace(e.getMessage());
            return response;
        } finally {
            logger.info("[API-MIGRABLOCK-LOG] Completed executeCvlanBlockRemove operation for filter: {}", filter);
        }
    }

    private RemoveCvlanBlockResponse createRemoveCvlanBlockResponse(OperationResult operationResult, Long idCvlan) {
        RemoveCvlanBlockResponse response = new RemoveCvlanBlockResponse();
        response.setOperationResult(operationResult);

        if (Status.SUCCESS.equals(operationResult.getStatus())) {
            response.setId(idCvlan);
        }
        return response;
    }
}
