package com.ws.ont.repository;

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

    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private AuditoriaLogOntRepository auditoriaLog;

    public ListOntBlockResponse getCvlanBlockList(ListOntBlockFilter filter) {
        String query = ListOntBlocksSql.getQueryListOntBlock(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        ListOntBlockResponse response = new ListOntBlockResponse(resultTuples);
        return response;
    }

    public RemoveOntBlockResponse executeOntBlockRemove(RemoveOntBlockFilter filter) {

        Long id = checkOntBlockExists(filter);

        if (id == null) {
            System.out.println("Não existe esse mano");

        }

        // removeOntBlock(id);

        //update no campo de observação da ONT

        auditoriaLog.insertAuditLog(new AuditoriaLogOnt(filter, filter.getLogin(), filter.getSystemOrigin(), filter.getRemoveBlockReason(), Operation.UNBLOCK_ONT));

        return null;

    }


    public Long checkOntBlockExists(RemoveOntBlockFilter input) {
        String query = CheckOntExistsSql.getQueryCheckOntExists(input, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return getLong(resultTuples.get(0), OntExistStructureAttr.CTP_ID);
    }

    public void removeOntBlock(Long id) {

        RemoveOntBlocksSql.executeRemoveOntBlock(jdbcTemplate, id);
    }


}
