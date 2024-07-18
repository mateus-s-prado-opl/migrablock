package com.ws.ont.repository;

import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.ont.sql.CheckOntExistsSql;
import com.ws.ont.sql.ListOntBlocksSql;
import com.ws.ont.enums.OntExistStructureAttr;
import com.ws.ont.filter.RemoveOntBlockFilter;
import com.ws.ont.pojo.response.ListOntBlockResponse;
import com.ws.ont.pojo.response.RemoveOntBlockResponse;
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

    public ListOntBlockResponse getCvlanBlockList(ListCvlanBlockFilter filter) {
        String query = ListOntBlocksSql.getQueryListOntBlock(filter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        ListOntBlockResponse response = new ListOntBlockResponse(resultTuples);
        return response;
    }

    public RemoveOntBlockResponse executeOntBlockRemove(RemoveOntBlockFilter input)  {

       Long id = checkOntBlockExists(input);

        if (id==null) {
            System.out.println("Não existe esse mano");

        }

        removeOntBlock(id);

        return null;

    }


    public Long checkOntBlockExists(RemoveOntBlockFilter input) {
        String query = CheckOntExistsSql.getQueryCheckOntExists(input, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return getLong(resultTuples.get(0), OntExistStructureAttr.CTP_ID);
    }

    public void removeOntBlock(Long input) {

    }


}
