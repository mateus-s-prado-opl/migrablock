package com.ws.cvlan.sql.auditoria;

import com.ws.cvlan.pojo.AuditoriaLog;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class InsertAuditoriaLogSql {


    private static final String QUERY_INSERT_AUDITORIA_LOG =
            "INSERT INTO AUDIT_LOG_MIG_BLOCK_CVLAN (" +
                    "    ID, USER_CREATED, DATE_CREATED, OPERATION, SYSTEM, COMMENTS, PROCESS_ID" +
                    ") VALUES (" +
                    "    SEQ_AUDIT_LOG_MIG_BLOCK_CVLAN.nextval, :userCreated, :dateCreated, :operation, :system, :comments, :processId" +
                    ") ";

    public static String getQueryInsertAuditoriaLog(AuditoriaLog auditoriaLog, MapSqlParameterSource sqlParameterSource) {
        sqlParameterSource.addValue("userCreated", auditoriaLog.getUserCreated());
        sqlParameterSource.addValue("dateCreated", auditoriaLog.getDateCreated());
        sqlParameterSource.addValue("operation", auditoriaLog.getOperation().getDescription());
        sqlParameterSource.addValue("system", auditoriaLog.getSystem());
        sqlParameterSource.addValue("comments", auditoriaLog.getComments());
        sqlParameterSource.addValue("processId", auditoriaLog.getProcessId());

        return QUERY_INSERT_AUDITORIA_LOG;
    }
}
