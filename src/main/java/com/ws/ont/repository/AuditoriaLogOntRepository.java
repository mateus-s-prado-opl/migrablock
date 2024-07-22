package com.ws.ont.repository;

import com.ws.ont.pojo.AuditoriaLogOnt;
import com.ws.ont.sql.auditoria.InsertAuditoriaLogSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AuditoriaLogOntRepository {


    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public AuditoriaLogOntRepository() {
    }

    @Transactional
    public void insertAuditLog(AuditoriaLogOnt auditoriaLogOnt) {
        String query = InsertAuditoriaLogSql.getQueryInsertAuditoriaLog(auditoriaLogOnt, sqlParameterSource);
        try {
            jdbcTemplate.update(query, sqlParameterSource);
        } catch (Exception e) {
            System.out.println("Erro ao inserir o log de auditoria:");
            System.out.println("Mensagem de erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
