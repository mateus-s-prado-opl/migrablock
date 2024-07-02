package com.ws.cvlan.repository;

import com.ws.cvlan.enums.*;
import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.cvlan.pojo.AddCvlanBlock;
import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import com.ws.cvlan.pojo.DTOs.CreateMessageCheckCvlanBlockExistsDTO;
import com.ws.cvlan.pojo.response.CvlanBlockListResponse;
import com.ws.cvlan.sql.CVLAN.AddCvlanBlockSql;
import com.ws.cvlan.sql.CVLAN.CheckCvlanBlockExists;
import com.ws.cvlan.sql.CVLAN.FindServiceByCvlanSql;
import com.ws.cvlan.sql.CVLAN.ListCvlanBlocksSql;
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


    public boolean serviceExistByCvlan(AddCvlanBlockFilter addCvlanBlockFilter) {
        String query = FindServiceByCvlanSql.getQueryFindServiceByCvlan(addCvlanBlockFilter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return !resultTuples.isEmpty() && !getString(resultTuples.get(0), CvlanExistStructureAttr.NAME).isEmpty();
    }

    public CreateMessageCheckCvlanBlockExistsDTO checkCvlanBlockExists(AddCvlanBlockFilter addCvlanBlockFilter) {
        CheckCvlanBlockExistsDTO checkCvlanBlockExistsDTO = new CheckCvlanBlockExistsDTO(
                addCvlanBlockFilter.getSvlan(),
                addCvlanBlockFilter.getCvlan()
        );

        String query = CheckCvlanBlockExists.getQueryCheckCvlanBlockExists(checkCvlanBlockExistsDTO, sqlParameterSource);
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
    public AddCvlanBlock addCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter) {

        if (!serviceExistByCvlan(addCvlanBlockFilter)) {
            return createResponseAddCvlanBlock(OperationResult.CVLAN_NOT_FOUND, null);
        }

        CreateMessageCheckCvlanBlockExistsDTO cvlanAlreadyBlocked = checkCvlanBlockExists(addCvlanBlockFilter);
        if (cvlanAlreadyBlocked.isExist()) {
            AddCvlanBlock response = createResponseAddCvlanBlock(OperationResult.CVLAN_BLOCKED, cvlanAlreadyBlocked.getProcessId());
            response.setMessage(cvlanAlreadyBlocked.getMessage());
            return response;
        }

        try {
            return executeCvlanBlockInsertion(addCvlanBlockFilter);
        } catch (Exception e) {
            return createResponseAddCvlanBlock(OperationResult.UNKNOWN_ERROR, null);
        }
    }

    private AddCvlanBlock executeCvlanBlockInsertion(AddCvlanBlockFilter addCvlanBlockFilter) {
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
        return createResponseAddCvlanBlock(OperationResult.SUCCESS, processId);
    }

    private AddCvlanBlock createResponseAddCvlanBlock(OperationResult operationResult, Long idCvlan) {
        AddCvlanBlock cvlanBlock = new AddCvlanBlock();
        cvlanBlock.setOperationResult(operationResult);

        if (Status.SUCCESS.equals(operationResult.getStatus())) {
            cvlanBlock.setId(idCvlan);
        }
        return cvlanBlock;
    }


    public CvlanBlockListResponse getCvlanBlockList(ListCvlanBlockFilter filter) {
        String query = ListCvlanBlocksSql.getQueryListCvlanBlock(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return new CvlanBlockListResponse(resultTuples);

    }

}
