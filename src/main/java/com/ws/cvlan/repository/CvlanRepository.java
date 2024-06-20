package com.ws.cvlan.repository;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ws.cvlan.pojo.CvlanBlock;
import com.ws.cvlan.sql.CvlanSql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;


@Repository
public class CvlanRepository {


    @PersistenceContext
    private EntityManager em;


    private NamedParameterJdbcTemplate jdbcTemplate;

    public CvlanBlock addCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter) {

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        String query = CvlanSql.getQueryAddCvlanBlock(addCvlanBlockFilter, sqlParameterSource);
        //List<Map<String, Object>> tuples = jdbcTemplate.queryForList(query, sqlParameterSource);

        return new CvlanBlock();

    }
}
