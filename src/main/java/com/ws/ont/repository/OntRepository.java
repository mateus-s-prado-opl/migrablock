package com.ws.ont.repository;

import com.ws.cvlan.enums.OperationResult;
import com.ws.cvlan.enums.Status;
import com.ws.ont.enums.OntExistStructureAttr;
import com.ws.ont.enums.Operation;
import com.ws.ont.filter.ListOntBlockFilter;
import com.ws.ont.filter.RemoveOntBlockFilter;
import com.ws.ont.pojo.AuditoriaLogOnt;
import com.ws.ont.pojo.response.ListOntBlockResponse;
import com.ws.ont.pojo.response.RemoveOntBlockResponse;
import com.ws.ont.sql.ont.CheckOntExistsSql;
import com.ws.ont.sql.ont.ListOntBlocksSql;
import com.ws.ont.sql.ont.RemoveOntBlocksSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;

@Repository
public class OntRepository {

    private static final Logger logger = LoggerFactory.getLogger(OntRepository.class);
    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private AuditoriaLogOntRepository auditoriaLog;

    public ListOntBlockResponse getOntBlockList(ListOntBlockFilter filter) {
        String query = ListOntBlocksSql.getQueryListOntBlock(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return new ListOntBlockResponse(resultTuples);
    }

    public RemoveOntBlockResponse executeOntBlockRemove(RemoveOntBlockFilter filter) {
        logger.info("[API-MIGRABLOCK-LOG] Starting executeOntBlockRemove operation");

        Long idOnt = checkOntBlockExists(filter);
        if (idOnt == null) {
            return createRemoveOntBlockResponse(OperationResult.BLOCK_NOT_FOUND, null);
        }

        try {
            removeOntBlock(filter, idOnt);
            return createRemoveOntBlockResponse(OperationResult.SUCCESS, idOnt);
        } catch (Exception e) {
            return handleRemovalException(e);
        } finally {
            logger.info("[API-MIGRABLOCK-LOG] Completed executeOntBlockRemove operation");
        }
    }

    private Long checkOntBlockExists(RemoveOntBlockFilter filter) {
        String query = CheckOntExistsSql.getQueryCheckOntExists(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return resultTuples.isEmpty() ? null : getLong(resultTuples.get(0), OntExistStructureAttr.CTP_ID);
    }

    private RemoveOntBlockResponse createRemoveOntBlockResponse(OperationResult operationResult, Long idOnt) {
        RemoveOntBlockResponse response = new RemoveOntBlockResponse();
        response.setOperationResult(operationResult);

        if (Status.SUCCESS.equals(operationResult.getStatus())) {
            response.setId(idOnt);
        }
        return response;
    }

    private void removeOntBlock(RemoveOntBlockFilter filter, Long idOnt) {
        RemoveOntBlocksSql.executeRemoveOntBlock(jdbcTemplate, idOnt);

        //TODO: Criar função apra inserir observação

        AuditoriaLogOnt auditLog = new AuditoriaLogOnt(
                filter,
                filter.getLogin(),
                filter.getSystemOrigin(),
                filter.getRemoveBlockReason(),
                Operation.UNBLOCK_ONT,
                idOnt
        );
        auditoriaLog.insertAuditLog(auditLog);
    }

    private RemoveOntBlockResponse handleRemovalException(Exception e) {
        logger.error("[API-MIGRABLOCK-LOG] Error occurred during executeOntBlockRemove operation", e);
        RemoveOntBlockResponse response = createRemoveOntBlockResponse(OperationResult.UNKNOWN_ERROR, null);
        response.setStackTrace(e.getMessage());
        return response;
    }
}
