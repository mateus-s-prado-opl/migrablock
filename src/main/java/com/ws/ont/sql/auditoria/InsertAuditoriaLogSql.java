package com.ws.ont.sql.auditoria;

import com.ws.ont.pojo.AuditoriaLogOnt;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class InsertAuditoriaLogSql {

    private static final String QUERY_INSERT_AUDITORIA_LOG =
            "INSERT INTO AUDIT_LOG_MIG_BLOCK_ONT (" +
                    "ID, USER_CREATED, DATE_CREATED, STATE_ABBREVIATION, STATE_NAME, " +
                    "LOCALITY_ABBREVIATION, LOCALITY_NAME, OLT_NAME, OLT_UID, " +
                    "PON_INTERFACE, ONT_ID, OPERATION, SYSTEM, COMMENTS, PROCESS_ID" +
                    ") VALUES (" +
                    "SEQ_AUDIT_LOG_MIG_BLOCK_ONT.nextval, :userCreated, :dateCreated, :stateAbbreviation, :stateName, " +
                    ":localityAbbreviation, :localityName, :oltName, :oltUid, " +
                    ":ponInterface, :ontId, :operation, :system, :comments, :processId )";

    public static String getQueryInsertAuditoriaLog(AuditoriaLogOnt auditoriaLogOnt, MapSqlParameterSource sqlParameterSource) {
        sqlParameterSource.addValue("userCreated", auditoriaLogOnt.getUserCreated());
        sqlParameterSource.addValue("dateCreated", auditoriaLogOnt.getDateCreated());
        sqlParameterSource.addValue("stateAbbreviation", auditoriaLogOnt.getStateAbbreviation());
        sqlParameterSource.addValue("stateName", auditoriaLogOnt.getStateName());
        sqlParameterSource.addValue("localityAbbreviation", auditoriaLogOnt.getLocalityAbbreviation());
        sqlParameterSource.addValue("localityName", auditoriaLogOnt.getLocalityName());
        sqlParameterSource.addValue("oltName", auditoriaLogOnt.getOltName());
        sqlParameterSource.addValue("oltUid", auditoriaLogOnt.getOltUid());
        sqlParameterSource.addValue("ponInterface", auditoriaLogOnt.getPonInterface());
        sqlParameterSource.addValue("ontId", auditoriaLogOnt.getOntId());
        sqlParameterSource.addValue("operation", auditoriaLogOnt.getOperation().getDescription());
        sqlParameterSource.addValue("system", auditoriaLogOnt.getSystem());
        sqlParameterSource.addValue("comments", auditoriaLogOnt.getComments());
        sqlParameterSource.addValue("processId", auditoriaLogOnt.getIdProcess());


        return QUERY_INSERT_AUDITORIA_LOG;
    }
}
