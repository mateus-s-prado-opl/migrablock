package com.ws.cvlan.repository;

import com.ws.cvlan.pojo.AuditoriaLog;
import com.ws.cvlan.sql.auditoria.InsertAuditoriaLogSql;
import com.ws.utils.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AuditoriaLogRepository {


    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public AuditoriaLogRepository() {
    }

    @Transactional
    public void insertAuditLog(String login, String systemOrigin, Operation operation, String comments, Long processId) {

        AuditoriaLog auditoriaLog = new AuditoriaLog(
                login, operation, systemOrigin, comments, processId
        );

        String query = InsertAuditoriaLogSql.getQueryInsertAuditoriaLog(auditoriaLog, sqlParameterSource);
//        System.out.println("Query SQL gerada para inserir o log de auditoria:");
//        System.out.println(query);
//        System.out.println("Parâmetros da query: " + sqlParameterSource.getValues());

        try {
           // System.out.println("Executando a query para inserir o log de auditoria...");
            jdbcTemplate.update(query, sqlParameterSource);
           // System.out.println("Log de auditoria inserido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao inserir o log de auditoria:");
            System.out.println("Mensagem de erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
