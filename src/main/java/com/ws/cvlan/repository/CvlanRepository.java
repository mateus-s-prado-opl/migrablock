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
import javax.transaction.Transactional;
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


    @Transactional
    public AddCvlanBlock addCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter) {

        System.out.println("Iniciando verificação da existência do serviço para o filtro fornecido...");
        boolean serviceExists = serviceExistByCvlan(addCvlanBlockFilter);
        System.out.println("Serviço existente: " + serviceExists);

        if (!serviceExists) {
            System.out.println("Serviço não encontrado para o filtro fornecido. Retornando erro.");
            return createErrorCvlanNotFound();
        }

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        String query = CvlanSql.getQueryAddCvlanBlock(addCvlanBlockFilter, sqlParameterSource);

        System.out.println("Query gerada para adicionar o bloqueio de CVLAN:");
        System.out.println(query);
        System.out.println("Parâmetros da query: " + sqlParameterSource.getValues());

        try {
            System.out.println("Executando a query para bloquear o CVLAN...");
            jdbcTemplate.update(query, sqlParameterSource);
            System.out.println("CVLAN bloqueada com sucesso.");

            AddCvlanBlock response = new AddCvlanBlock();
            response.setStatus(Status.SUCCESS);
            response.setMessage("CVLAN bloqueada com sucesso.");
            return response;
        } catch (Exception e) {
            System.out.println("Erro ao bloquear o CVLAN:");
            System.out.println("Mensagem de erro: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace para detalhes adicionais
            return createErrorCvlanNotFound();
        }
    }

    private AddCvlanBlock createErrorCvlanNotFound() {
        AddCvlanBlock cvlanBlock = new AddCvlanBlock();
        cvlanBlock.setStatus(Status.ERROR);
        cvlanBlock.setMessage("CVLAN service does not exist for the given filter.");
        return cvlanBlock;
    }


    public RemoveCvlanBlock removeCvlanBlock(RemoveCvlanBlockFilter removeCvlanBlockFilter) {

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        //String query = CvlanSql.getQueryRemoveCvlanBlock(removeCvlanBlockFilter, sqlParameterSource);
        //List<Map<String, Object>> tuples = jdbcTemplate.queryForList(query, sqlParameterSource);

        return new RemoveCvlanBlock();

    }
}
