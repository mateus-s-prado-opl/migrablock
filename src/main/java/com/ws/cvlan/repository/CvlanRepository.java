package com.ws.cvlan.repository;

import com.ws.cvlan.enums.CvlanExistStructureAttr;
import com.ws.cvlan.enums.Status;
import com.ws.cvlan.filter.AddCvlanBlockFilter;

import com.ws.cvlan.filter.RemoveCvlanBlockFilter;
import com.ws.cvlan.pojo.RemoveCvlanBlock;
import com.ws.cvlan.sql.FindServiceByCvlanSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ws.cvlan.pojo.AddCvlanBlock;
import com.ws.cvlan.sql.CvlanSql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getString;


@Repository
public class CvlanRepository {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public CvlanRepository() {
    }

    public boolean serviceExistByCvlan(AddCvlanBlockFilter addCvlanBlockFilter){
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        String query = FindServiceByCvlanSql.getQueryFindServiceByCvlan(addCvlanBlockFilter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);

        if (resultTuples.isEmpty()) {
            return false;
        }

        String serviceName = getString(resultTuples.get(0), CvlanExistStructureAttr.NAME);
        return !serviceName.isEmpty();
    }


    public AddCvlanBlock addCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter) {
        if(!serviceExistByCvlan(addCvlanBlockFilter)){
            return createErrorCvlanNotFound();
        }


//        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
//        String query = CvlanSql.getQueryAddCvlanBlock(addCvlanBlockFilter, sqlParameterSource);
//        List<Map<String, Object>> tuples = jdbcTemplate.queryForList(query, sqlParameterSource);

        return new AddCvlanBlock();

    }

    private AddCvlanBlock createErrorCvlanNotFound() {
        AddCvlanBlock cvlanBlock = new AddCvlanBlock();
        cvlanBlock.setStatus(Status.ERROR);
        cvlanBlock.setMessage("CVLAN service does not exist for the given filter.");
        return cvlanBlock;
    }


    public RemoveCvlanBlock removeCvlanBlock(RemoveCvlanBlockFilter removeCvlanBlockFilter) {

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        String query = CvlanSql.getQueryRemoveCvlanBlock(removeCvlanBlockFilter, sqlParameterSource);
        //List<Map<String, Object>> tuples = jdbcTemplate.queryForList(query, sqlParameterSource);

        return new RemoveCvlanBlock();

    }
}
