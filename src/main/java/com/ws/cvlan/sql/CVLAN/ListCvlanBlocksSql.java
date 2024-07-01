package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.filter.ListCvlanBlockFilter;
import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class ListCvlanBlocksSql {

    private static final StringBuilder queryCheckCvlanBlockExists =
            new StringBuilder()
                    .append(" SELECT ALMB.PROCESS_ID, ALMB.USER_CREATED, ALMB.DATE_CREATED, ALMB.COMMENTS ")
                    .append(" FROM MIG_BLOCKED_CVLAN CB ")
                    .append(" INNER JOIN AUDIT_LOG_MIG_BLOCK_CVLAN ALMB ON ALMB.PROCESS_ID = CB.ID  ")
                    .append(" WHERE 1=1 ");

    private static void addWhere(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {
        if (filter.getSvlan() != null) {
            finalQuery.append(" AND CB.svlan = :svlan ");
            namedParameters.addValue("svlan", filter.getSvlan());
        }
        if (filter.getCvlan() != null) {
            finalQuery.append(" AND CB.cvlan = :cvlan ");
            namedParameters.addValue("cvlan", filter.getCvlan());
        }
    }

    public static String getQueryListCvlanBlock(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters){
        StringBuilder finalQuery = new StringBuilder(queryCheckCvlanBlockExists);
        addWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }
}
