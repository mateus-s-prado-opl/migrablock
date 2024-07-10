package com.ws.cvlan.repository;

import com.ws.cvlan.enums.*;
import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.cvlan.filter.RemoveCvlanBlockFilter;
import com.ws.cvlan.filter.validation.BaseCvlanFilter;
import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import com.ws.cvlan.pojo.DTOs.CreateMessageCheckCvlanBlockExistsDTO;
import com.ws.cvlan.pojo.response.AddCvlanBlockResponse;
import com.ws.cvlan.pojo.response.CvlanBlockListResponse;
import com.ws.cvlan.pojo.response.RemoveCvlanBlockResponse;
import com.ws.cvlan.sql.CVLAN.*;
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

import static com.ws.cvlan.util.StringUtilSol.getLong;
import static com.ws.cvlan.util.StringUtilSol.getString;

@Repository
public class CvlanRepository {

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

    public CreateMessageCheckCvlanBlockExistsDTO checkCvlanBlockExists(BaseCvlanFilter baseCvlanFilter) {
        CheckCvlanBlockExistsDTO checkCvlanBlockExistsDTO = new CheckCvlanBlockExistsDTO(
                baseCvlanFilter.getSvlan(),
                baseCvlanFilter.getCvlan()
        );

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

    @Transactional
    public AddCvlanBlockResponse addCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter) {

        if (!serviceExistByCvlan(addCvlanBlockFilter)) {
            return createAddCvlanBlockResponse(OperationResult.CVLAN_NOT_FOUND, null);
        }

        CreateMessageCheckCvlanBlockExistsDTO cvlanAlreadyBlocked = checkCvlanBlockExists(addCvlanBlockFilter);
        if(cvlanAlreadyBlocked.isExist()){

            if(isBlocked(cvlanAlreadyBlocked)){
                AddCvlanBlockResponse response = createAddCvlanBlockResponse(OperationResult.CVLAN_BLOCKED, cvlanAlreadyBlocked.getProcessId());
                response.setMessage(cvlanAlreadyBlocked.getMessage());
                return response;
            } else{
                return executeCvlanBlockUpdate(addCvlanBlockFilter, cvlanAlreadyBlocked.getProcessId());
            }
        }

        return executeCvlanBlockInsertion(addCvlanBlockFilter);
    }

    private boolean isBlocked(CreateMessageCheckCvlanBlockExistsDTO cvlanAlreadyBlocked) {
        return cvlanAlreadyBlocked.getStatusCVLAN() == StatusCVLAN.BLOCKED.getValue();
    }

    private AddCvlanBlockResponse executeCvlanBlockInsertion(AddCvlanBlockFilter addCvlanBlockFilter) {
        String query = AddCvlanBlockSql.getQueryAddCvlanBlock(addCvlanBlockFilter, sqlParameterSource);
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

    public CvlanBlockListResponse getCvlanBlockList(ListCvlanBlockFilter filter) {
        String query = ListCvlanBlocksSql.getQueryListCvlanBlock(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return new CvlanBlockListResponse(resultTuples);
    }

    @Transactional
    public RemoveCvlanBlockResponse executeCvlanBlockRemove(RemoveCvlanBlockFilter filter) {

        CreateMessageCheckCvlanBlockExistsDTO cvlanAlreadyBlocked = checkCvlanBlockExists(filter);
        if (cvlanAlreadyBlocked.isExist() && isBlocked(cvlanAlreadyBlocked)) {
            String query = RemoveCvlanBlocksSql.getQueryRemoveCvlanBlock(filter, sqlParameterSource);

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
