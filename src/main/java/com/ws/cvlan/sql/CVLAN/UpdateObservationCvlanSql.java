package com.ws.cvlan.sql.CVLAN;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class UpdateObservationCvlanSql {

    private static final StringBuilder BASE_UPDATE_QUERY =
            new StringBuilder()
                        .append(" UPDATE NS_RES_INS_NODE ")
                    .append(" SET ADDITIONAL_INFORMATION = :comments ")
                    .append(" WHERE ID IN (")
                    .append("   SELECT NODE.ID ")
                    .append("   FROM ISP_INS_EQUIPAMENTO EQUIP ")
                    .append("   INNER JOIN NS_RES_INS_NODE_MIRROR MIRROR ")
                    .append("   ON MIRROR.ID_ISP = EQUIP.ID_BD_EQUIPAMENTO ")
                    .append("   INNER JOIN NS_RES_INS_NODE NODE ")
                    .append("   ON NODE.ID = MIRROR.ID_NODE ")
                    .append("   WHERE EQUIP.UNIQUE_ID = :uniqueId ")
                    .append(" )");

    private static void addWhere(String oltUid, String comments, MapSqlParameterSource namedParameters) {
        namedParameters.addValue("uniqueId", oltUid);
        namedParameters.addValue("comments", comments);
    }

    public static String getUpdateObservationQuery(String oltUid, String comments, MapSqlParameterSource sqlParameterSource) {
        String finalQuery = String.valueOf(BASE_UPDATE_QUERY);
        addWhere(oltUid, comments, sqlParameterSource);
        return finalQuery;
    }
}
