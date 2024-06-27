package com.ws.cvlan.sql;

import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class CheckCvlanBlockExists {

    private static final StringBuilder queryCheckCvlanBlockExists =
            new StringBuilder()
                    .append(" SELECT COUNT(*) AS STATUS ")
                    .append(" FROM MIG_BLOCKED_CVLAN CB ")
                    .append(" WHERE 1=1 ");

    public static String getQueryCheckCvlanBlockExists(CheckCvlanBlockExistsDTO filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(queryCheckCvlanBlockExists);
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
