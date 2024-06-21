package com.ws.cvlan.sql;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class FindServiceByCvlanSql {

    private static final StringBuilder queryFindServiceByCvlan =
            new StringBuilder()
                    .append("   SELECT MAX(SER.NAME) AS SERVICE_NAME ")
                    .append("   FROM NS_SER_INS_SERVICE SER ")
                    .append("   INNER JOIN NS_SER_CAT_SERVICE CS ON CS.ID_BD_CAT_SERVICE = SER.ID_BD_CAT_SERVICE AND CS.TYPE = 'CFS.SVLAN' ")
                    .append("   INNER JOIN NI_DYN_INS_ATTRIBUTE IA ON IA.ID_BD_INS_ENTITY = SER.ID ")
                    .append("   INNER JOIN NI_DYN_CAT_ATTRIBUTE CA ON CA.ID_BD_CAT_ATTRIBUTE = IA.ID_BD_CAT_ATTRIBUTE AND CA.ATTRIBUTE_NAME = 'svlan_profile' ")
                    .append("   INNER JOIN NS_SER_INS_SERVIC_SERVIC SS ON SS.ID_SERVICE_PARENT = SER.ID ")
                    .append("   INNER JOIN NS_SER_INS_SERVICE_RESOUR SR ON SR.ID_BD_SERVICE = SS.ID_SERVICE ")
                    .append("   INNER JOIN NS_RES_INS_PIPE_TP PT ON PT.ID_PIPE = SR.ID_BD_RES_PIPE ")
                    .append("   INNER JOIN NS_RES_INS_TP TPS ON TPS.ID = PT.ID_TP ")
                    .append("   INNER JOIN NS_RES_INS_TP_TP_ALL TP0 ON TP0.ID_TP_PARENT = TPS.ID ")
                    .append("   INNER JOIN NS_RES_INS_TP_ALL TPS2 ON TPS2.ID = TP0.ID_TP ")
                    .append("   INNER JOIN NS_RES_INS_TP_TP TP1 ON TP1.ID_TP = TPS.ID ")
                    .append("   INNER JOIN NS_RES_INS_TP_TP TP2 ON TP2.ID_TP = TP1.ID_TP_PARENT ")
                    .append("   INNER JOIN NS_RES_INS_TP_TP TP3 ON TP3.ID_TP = TP2.ID_TP_PARENT ")
                    .append("   INNER JOIN NS_RES_INS_TP_NODE NO ON NO.ID_TP = TP3.ID_TP_PARENT ")
                    .append("   INNER JOIN NS_RES_INS_NODE NO2 ON NO2.ID = NO.ID_NODE ")
                    .append(" WHERE 1=1 ");

    public static String getQueryFindServiceByCvlan(AddCvlanBlockFilter addCvlanBlockFilter , MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(queryFindServiceByCvlan);
        addWhere(addCvlanBlockFilter, namedParameters, finalQuery);
        //addOrderBy(cvlanFilter, namedParameters, finalQuery);
        return finalQuery.toString();
    }

    private static void addWhere(AddCvlanBlockFilter addCvlanFilter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {

        if (addCvlanFilter.getOntId() != null) {
            finalQuery.append("AND NO2.ID = :id_olt_ns ");
            namedParameters.addValue("id_olt_ns", addCvlanFilter.getOntId());
        }
        if (addCvlanFilter.getSvlan() != null) {
            finalQuery.append("AND TPS.NAME = :svlan ");
            namedParameters.addValue("svlan", addCvlanFilter.getSvlan());
        }
        if (addCvlanFilter.getCvlan() != null) {
            finalQuery.append("AND TPS2.NAME = :cvlan ");
            namedParameters.addValue("cvlan", addCvlanFilter.getCvlan());
        }
    }
}
