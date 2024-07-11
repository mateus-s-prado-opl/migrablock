package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.enums.StatusCVLAN;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class RemoveCvlanBlocksSql {

    private static final StringBuilder QUERY_REMOVE_CVLAN_BLOCKS =
            new StringBuilder()
                    .append(" UPDATE MIG_BLOCKED_CVLAN CB SET IS_BLOCKED = :isNotBlocked ")
                    .append(" WHERE ID = :idBlock ");

    private static void addWhere(Long idBlock, MapSqlParameterSource namedParameters) {
        namedParameters.addValue("isNotBlocked", StatusCVLAN.NOT_BLOCKED.getValue());
        namedParameters.addValue("idBlock", idBlock);
    }

    public static String getQueryRemoveCvlanBlock(Long idBlock, MapSqlParameterSource namedParameters) {
        String finalQuery = String.valueOf(QUERY_REMOVE_CVLAN_BLOCKS);
        addWhere(idBlock, namedParameters);
        return finalQuery;
    }
}
