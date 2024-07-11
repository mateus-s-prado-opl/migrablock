package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class CheckCvlanBlockExistsSql {

    private static final StringBuilder QUERY_BASE =
            new StringBuilder()
                    .append(" SELECT ALMB.PROCESS_ID, ALMB.USER_CREATED, ALMB.DATE_CREATED, ALMB.COMMENTS, CB.IS_BLOCKED ")
                    .append(" FROM MIG_BLOCKED_CVLAN CB ")
                    .append(" INNER JOIN AUDIT_LOG_MIG_BLOCK_CVLAN ALMB ON ALMB.PROCESS_ID = CB.ID  ")
                    .append(" WHERE 1=1 ");

    public static String getQueryCheckCvlanBlockExists(CheckCvlanBlockExistsDTO filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(QUERY_BASE);
        addWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }

    private static void addWhere(CheckCvlanBlockExistsDTO filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {
        if (filter.getSvlan() != null) {
            finalQuery.append(" AND CB.svlan = :svlan ");
            namedParameters.addValue("svlan", filter.getSvlan());
        }
        if (filter.getCvlan() != null) {
            finalQuery.append(" AND CB.cvlan = :cvlan ");
            namedParameters.addValue("cvlan", filter.getCvlan());
        }
    }
}
