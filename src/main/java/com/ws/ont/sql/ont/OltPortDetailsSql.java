package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class OltPortDetailsSql {

    private static final String QUERY_BASE =
            "SELECT TP_AVO.ID AS OLT_PTP_ID, " +
                    "TP_AVO.NAME AS OLT_PTP_NAME, " +
                    "TP_PAI.ID AS OLT_TTP_ID, " +
                    "TP.ID AS OLT_CTP_ID, " +
                    "TP.NAME AS OLT_CTP_NAME " +
            "FROM NS_RES_INS_TP TP_AVO " +
                    "INNER JOIN NS_RES_INS_TP_MIRROR TP_AVO_M ON TP_AVO_M.ID_TP = TP_AVO.ID " +
                    "INNER JOIN ISP_INS_PORTO_FISICO PF ON PF.ID_BD_PORTO_FISICO = TP_AVO_M.ID_ISP " +
                    "INNER JOIN ISP_CAT_PORTO_FISICO CPF ON CPF.ID_BD_TIPO_PORTO_FISICO = PF.ID_BD_TIPO_PORTO_FISICO AND CPF.TIPO = 'GPON' " +
                    "INNER JOIN ISP_INS_EQUIPAMENTO IEQ ON IEQ.ID_BD_EQUIPAMENTO = PF.ID_BD_EQUIPAMENTO " +
                    "INNER JOIN ISP_CAT_TIPO_EQUIPAMENTO CTE ON CTE.ID_BD_TIPO_EQUIPAMENTO = IEQ.ID_BD_TIPO_NE AND CTE.SIGLA = 'OLT' " +
                    "LEFT JOIN NS_RES_INS_TP_TP TP_TP2 ON TP_AVO.ID = TP_TP2.ID_TP_PARENT " +
                    "LEFT JOIN NS_RES_INS_TP TP_PAI ON TP_TP2.ID_TP = TP_PAI.ID " +
                    "LEFT JOIN NS_RES_INS_TP_TP TP_TP1 ON TP_PAI.ID = TP_TP1.ID_TP_PARENT " +
                    "LEFT JOIN NS_RES_INS_TP TP ON TP_TP1.ID_TP = TP.ID AND TP.NAME = :ontId " +
                    "WHERE TP_AVO.ID = :oltPortNsId";

    private static void addParameters(Long oltPortNsId, Long ontId, MapSqlParameterSource parameters) {
        if (oltPortNsId == null) {
            throw new IllegalArgumentException("OLT Port NS ID must not be null");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters source must not be null");
        }
        parameters.addValue("oltPortNsId", oltPortNsId);
        parameters.addValue("ontId", String.valueOf(ontId));
    }

    public String getFindPortByOltQuery(Long oltPortNsId, Long ontId, MapSqlParameterSource parameters) {
        addParameters(oltPortNsId, ontId, parameters);
        return QUERY_BASE;
    }
}
