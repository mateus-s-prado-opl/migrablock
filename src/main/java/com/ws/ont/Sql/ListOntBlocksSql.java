package com.ws.ont.Sql;

import com.ws.cvlan.filter.ListCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class ListOntBlocksSql {

    private static final StringBuilder QUERY_BASE =
            new StringBuilder()
                    .append("   SELECT ")
                    .append("     CTP.ID AS CTP_ID, ")
                    .append("     SERPARENT.NAME AS SERVICE_NAME, ")
                    .append("     CTP.USER_CREATE AS USER_CREATE, ")
                    .append("     NODE.NAME AS OLT, ")
                    .append("     PTP.NAME AS INTERFACE_PON, ")
                    .append("     CTP.NAME AS ONT_ID, ")
                    .append("     SLOT.CODIFICACAO AS SLOT ")
                    .append(" FROM ")
                    .append("     NS_RES_INS_NODE NODE ")
                    .append(" JOIN NS_RES_CAT_NODE CATNODE ON CATNODE.ID_BD_CAT_NODE = NODE.ID_BD_CAT_NODE ")
                    .append("     AND CATNODE.TYPE = 'ME.OLT' ")
                    .append(" JOIN NS_RES_INS_TP_NODE TPN ON TPN.ID_NODE = NODE.ID ")
                    .append(" JOIN NS_RES_INS_TP PTP ON PTP.ID = TPN.ID_TP ")
                    .append(" JOIN NS_RES_CAT_TP CAT ON CAT.ID_BD_CAT_TP = PTP.ID_BD_CAT_TP ")
                    .append(" JOIN NS_RES_INS_TP_TP TP0 ON TP0.ID_TP_PARENT = PTP.ID ") // PTP > TTP.MULTI
                    .append(" JOIN NS_RES_INS_TP_TP TP1 ON TP1.ID_TP_PARENT = TP0.ID_TP ") // TTP.MULTI > CTP.GPON
                    .append(" JOIN NS_RES_INS_TP CTP ON CTP.ID = TP1.ID_TP ")
                    .append(" INNER JOIN NS_RES_INS_TP TP0_INS ON TP0_INS.ID = TP0.ID_TP ")
                    .append(" INNER JOIN NS_RES_CAT_TP TP0_INS_CAT ON TP0_INS_CAT.ID_BD_CAT_TP = TP0_INS.ID_BD_CAT_TP ")
                    .append("     AND TP0_INS_CAT.TYPE = 'TTP.MULTI' ")
                    .append(" INNER JOIN NS_RES_INS_TP TP1_INS ON TP1_INS.ID = TP1.ID_TP ")
                    .append(" INNER JOIN NS_RES_CAT_TP TP1_INS_CAT ON TP1_INS_CAT.ID_BD_CAT_TP = TP1_INS.ID_BD_CAT_TP ")
                    .append("     AND TP1_INS_CAT.TYPE = 'CTP.GPON' ")
                    .append(" INNER JOIN NS_RES_INS_TP_MIRROR TP_MIRROR ON TP_MIRROR.ID_TP = PTP.ID ")
                    .append(" INNER JOIN ISP_INS_PORTO_FISICO PORTA ON PORTA.ID_BD_PORTO_FISICO = TP_MIRROR.ID_ISP ")
                    .append(" INNER JOIN ISP_INS_SLOT SLOT ON PORTA.ID_BD_CARTA = SLOT.ID_BD_CARTA ")
                    .append(" LEFT JOIN NS_RES_INS_TP_TP TP2 ON TP2.ID_TP_PARENT = TP1.ID_TP ")
                    .append(" LEFT JOIN NS_RES_INS_PIPE_TP PITP ON PITP.ID_TP = TP2.ID_TP ")
                    .append(" LEFT JOIN NS_SER_INS_SERVICE_RESOUR SR ON SR.ID_BD_RES_PIPE = PITP.ID_PIPE ")
                    .append(" LEFT JOIN NS_SER_INS_SERVICE SERPARENT ON SERPARENT.ID = SR.ID_BD_SERVICE");

    public static String getQueryListOntBlock(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(QUERY_BASE);
        addWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }

    private static void addWhere(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {


    }
}
