package com.ws.ont.repository;

import com.ws.cvlan.enums.OperationResult;
import com.ws.cvlan.enums.Status;
import com.ws.ont.enums.OntExistStructureAttr;
import com.ws.ont.enums.Operation;
import com.ws.ont.filter.AddOntBlockFilter;
import com.ws.ont.filter.ListOntBlockFilter;
import com.ws.ont.filter.RemoveOntBlockFilter;
import com.ws.ont.pojo.AuditoriaLogOnt;
import com.ws.ont.pojo.DTOs.*;
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
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ws.cvlan.util.StringUtilSol.getLong;

@Repository
public class OntRepository {

    private static final Logger logger = LoggerFactory.getLogger(OntRepository.class);
    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    private final CreateOrGetTTPSql createOrGetTTPSql;
    private final CreateOrGetCTPSql createOrGetCtpSql;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private AuditoriaLogOntRepository auditoriaLog;

    @Autowired
    public OntRepository(DataSource dataSource) {
        this.createOrGetTTPSql = new CreateOrGetTTPSql(dataSource);
        this.createOrGetCtpSql = new CreateOrGetCTPSql(dataSource);
    }

    public ListOntBlockResponse getOntBlockList(ListOntBlockFilter filter) {
        String query = ListOntBlocksSql.getQueryListOntBlock(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return new ListOntBlockResponse(resultTuples);
    }

    @Transactional
    public AddOntBlockResponse executeOntBlockAdd(AddOntBlockFilter filter) {
        Optional<OltIdsDto> oltOptional = findOlt(filter.getOltName());
        if (!oltOptional.isPresent()) {
            return createAddOntBlockResponse(OperationResult.OLT_NOT_FOUND, null);
        }
        OltIdsDto olt = oltOptional.get();

        Optional<PortDetailsDto> portOptional = findPort(olt.getIdOltIsp(), filter.getOntId(), filter.getPonInterface());
        if (!portOptional.isPresent()) {
            return createAddOntBlockResponse(OperationResult.PORT_NOT_FOUND, null);
        }
        PortDetailsDto port = portOptional.get();

        if (port.getOltCtpId() != null) {
            return createAddOntBlockResponse(OperationResult.OLT_ALREADY_CREATED, null);
        }

        addOntBlock(filter, olt, port);

        return null;

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
        ListOltDto olts = getOltList(port.getOltPtpId(), filter.getOntId());

        for (OltDto oltDto : olts.getOltsList()) {

            if (oltDto.getOltPtpid() == null) {
                throw new IllegalArgumentException("Cannot find ptp " + port.getOltPtpId());
            }
            if (oltDto.getOltCtpId() != null) {
                throw new IllegalArgumentException("Already exists CTP.GPON to ptp " + port.getOltPtpId() + " ont_id " + filter.getOntId());
            }
            if (oltDto.getOltTtpId() != null) {
                System.out.println("TTP ja existe " + oltDto.getOltTtpId());
            }

            TtpResponseDto ttpDto = createOrGetTtp(port, oltDto);

            if (ttpDto.getVTtpId() == null) {
                throw new IllegalArgumentException("Cannot create TTP.MULTI ptp " + port.getOltPtpId() + " ont_id " + filter.getOntId());
            }
            updateNsResInsTp(ttpDto.getVTtpId(), filter.getLogin());

            CtpResponseDto ctpDto = createOrGetCtp(ttpDto.getVTtpId(), filter.getOntId());

            updateNsResInsTpGpon(ctpDto.getCtpGponId(), filter.getLogin());
        }
    }

    private ListOltDto getOltList(Long oltPtpId, Long ontId) {
        String query = new OltPortDetailsSql().getFindPortByOltQuery(oltPtpId, ontId, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return new ListOltDto(resultTuples);
    }

    private AddOntBlockResponse createAddOntBlockResponse(OperationResult operationResult, Long idOnt) {
        AddOntBlockResponse response = new AddOntBlockResponse();
        response.setOperationResult(operationResult);

        if (Status.SUCCESS.equals(operationResult.getStatus())) {
            response.setId(idOnt);
        }
        return response;
    }

    private TtpResponseDto createOrGetTtp(PortDetailsDto port, OltDto oltDto) {
        Map<String, Object> out = createOrGetTTPSql.execute(port.getOltPtpId(), oltDto.getOltPtpName(), "TTP.MULTI");
        return new TtpResponseDto(out);
    }

    private void updateNsResInsTp(Long vTtpId, String userCreated) {
        String query = new UpdateNsResInsTpSql().getUpdateNsResInsTpQuery(userCreated, vTtpId, sqlParameterSource);
        jdbcTemplate.update(query, sqlParameterSource);
    }

    private CtpResponseDto createOrGetCtp(Long vTtpId, Long ontId) {
        Map<String, Object> out = createOrGetCtpSql.execute(vTtpId, ontId, "CTP.GPON");
        return new CtpResponseDto(out);
    }


    private void updateNsResInsTpGpon(Long ctpGponId, String userCreated) {
        String query = new UpdateNsResInsTpGponSql().getUpdateNsResInsTpQuery(userCreated, ctpGponId, sqlParameterSource);
        jdbcTemplate.update(query, sqlParameterSource);
    }

    @Transactional
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
