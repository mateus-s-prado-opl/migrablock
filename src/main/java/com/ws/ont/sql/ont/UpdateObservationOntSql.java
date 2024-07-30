package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class UpdateObservationOntSql {

    private static final StringBuilder BASE_UPDATE_QUERY =
            new StringBuilder()
                    .append(" UPDATE NS_RES_INS_TP ")
                    .append(" SET ADDITIONAL_INFORMATION = :additionalInformation ")
                    .append(" WHERE ID IN (")
                    .append("   SELECT CTP.ID ")
                    .append("   FROM NS_RES_INS_NODE NODE ")
                    .append("   JOIN NS_RES_CAT_NODE CATNODE ON CATNODE.ID_BD_CAT_NODE = NODE.ID_BD_CAT_NODE ")
                    .append("     AND CATNODE.TYPE = 'ME.OLT' ")
                    .append("   JOIN NS_RES_INS_TP_NODE TPN ON TPN.ID_NODE = NODE.ID ")
                    .append("   JOIN NS_RES_INS_TP PTP ON PTP.ID = TPN.ID_TP ")
                    .append("   JOIN NS_RES_CAT_TP CAT ON CAT.ID_BD_CAT_TP = PTP.ID_BD_CAT_TP ")
                    .append("   JOIN NS_RES_INS_TP_TP TP0 ON TP0.ID_TP_PARENT = PTP.ID ") // PTP > TTP.MULTI
                    .append("   JOIN NS_RES_INS_TP_TP TP1 ON TP1.ID_TP_PARENT = TP0.ID_TP ") // TTP.MULTI > CTP.GPON
                    .append("   JOIN NS_RES_INS_TP CTP ON CTP.ID = TP1.ID_TP ")
                    .append("   WHERE CTP.ID = :ctpId ")
                    .append(" )");

    private static void addWhere(Long ctpId, String additionalInformation, MapSqlParameterSource namedParameters) {
        namedParameters.addValue("ctpId", ctpId);
        namedParameters.addValue("additionalInformation", additionalInformation);
    }

    public static String getUpdateObservationQuery(Long ctpId, String additionalInformation, MapSqlParameterSource sqlParameterSource) {
        String finalQuery = String.valueOf(BASE_UPDATE_QUERY);
        addWhere(ctpId, additionalInformation, sqlParameterSource);
        return finalQuery;
    }
}
