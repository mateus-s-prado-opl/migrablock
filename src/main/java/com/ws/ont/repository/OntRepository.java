package com.ws.ont.repository;

import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.ont.Sql.ListOntBlocksSql;
import com.ws.ont.pojo.response.ListOntBlockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

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
}
