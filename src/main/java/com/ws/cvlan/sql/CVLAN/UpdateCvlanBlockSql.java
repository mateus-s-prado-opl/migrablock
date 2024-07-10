package com.ws.cvlan.sql.CVLAN;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class UpdateCvlanBlockSql {

    private static final StringBuilder BASE_UPDATE_QUERY =
            new StringBuilder()
                    .append(" UPDATE MIG_BLOCKED_CVLAN CB SET IS_BLOCKED = 1 ")
                    .append(" WHERE ID = :idBlock");

    private static void addWhere(Long idBlock, MapSqlParameterSource namedParameters) {
        namedParameters.addValue("idBlock", idBlock);
    }

    public static String getQueryUpdateCvlanBlock(Long idBlock, MapSqlParameterSource sqlParameterSource) {
        String finalQuery = String.valueOf(BASE_UPDATE_QUERY);
        addWhere(idBlock, sqlParameterSource);
        return finalQuery;
    }
}
