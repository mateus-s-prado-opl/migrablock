package com.ws.ont.repository;

import com.ws.cvlan.enums.OperationResult;
import com.ws.cvlan.enums.Status;
import com.ws.ont.enums.OntExistStructureAttr;
import com.ws.ont.enums.Operation;
import com.ws.ont.filter.AddOntBlockFilter;
import com.ws.ont.filter.ListOntBlockFilter;
import com.ws.ont.filter.RemoveOntBlockFilter;
import com.ws.ont.pojo.AuditoriaLogOnt;
import com.ws.ont.pojo.DTOs.OltIdsDto;
import com.ws.ont.pojo.DTOs.PortDetailsDto;
import com.ws.ont.pojo.response.AddOntBlockResponse;
import com.ws.ont.pojo.response.ListOntBlockResponse;
import com.ws.ont.pojo.response.RemoveOntBlockResponse;
import com.ws.ont.sql.ont.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public AddOntBlockResponse executeOntBlockAdd(AddOntBlockFilter filter) {
        OltIdsDto olt = findOlt(filter.getOltName())
                .orElseThrow(() -> new IllegalArgumentException("OLT not found"));

        PortDetailsDto port = findPort(olt.getIdOltIsp(), filter.getOntId(), filter.getPonInterface())
                .orElseThrow(() -> new IllegalArgumentException("Port not found"));


        if (port.getOltCtpId() == null) {
            addOntBlock(filter, olt, port);
        }

        throw new IllegalArgumentException("ALREADY CREATED");
    }


    private Optional<OltIdsDto> findOlt(String oltName) {
        try {
            String query = new FindOltByNameSql().getFindOltByNameQuery(oltName, sqlParameterSource);
            Map<String, Object> resultTuples = jdbcTemplate.queryForMap(query, sqlParameterSource);
            return Optional.of(new OltIdsDto(resultTuples));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Optional<PortDetailsDto> findPort(Long idOltIsp, Long idOnt, String interfacePon) {
        try {
            String query = new FindPortByOltSql().getFindPortByOltQuery(idOnt, idOltIsp, interfacePon, sqlParameterSource);
            Map<String, Object> resultTuples = jdbcTemplate.queryForMap(query, sqlParameterSource);
            return Optional.of(new PortDetailsDto(resultTuples));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private void addOntBlock(AddOntBlockFilter filter, OltIdsDto olt, PortDetailsDto port) {
        createPortDetails(port, filter.getOntId());

    }

    private void createPortDetails(PortDetailsDto port, Long idOnt) {
        String query = new OltPortDetailsSql().getFindPortByOltQuery(port.getOltPtpId(), idOnt, sqlParameterSource);
        Map<String, Object> resultTuples = jdbcTemplate.queryForMap(query, sqlParameterSource);

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
