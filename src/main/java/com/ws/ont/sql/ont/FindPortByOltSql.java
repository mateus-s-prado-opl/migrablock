package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class FindPortByOltSql {

    private static final String QUERY_BASE =
            "SELECT " +
                    "    TP_AVO.ID AS olt_ptp_ID, " +
                    "    MAX(TP.ID) AS olt_ctp_ID, " +
                    "    MAX(TP.USER_CREATE) AS USER_CREATE " +
                    "FROM NS_RES_INS_TP TP_AVO " +
                    "INNER JOIN NS_RES_INS_TP_MIRROR TP_AVO_M ON TP_AVO_M.ID_TP = TP_AVO.ID " +
                    "INNER JOIN ISP_INS_PORTO_FISICO PF ON PF.ID_BD_PORTO_FISICO = TP_AVO_M.ID_ISP " +
                    "INNER JOIN ISP_CAT_PORTO_FISICO CPF ON CPF.ID_BD_TIPO_PORTO_FISICO = PF.ID_BD_TIPO_PORTO_FISICO AND CPF.TIPO = 'GPON' " +
                    "INNER JOIN ISP_INS_EQUIPAMENTO IEQ ON IEQ.ID_BD_EQUIPAMENTO = PF.ID_BD_EQUIPAMENTO " +
                    "INNER JOIN ISP_CAT_TIPO_EQUIPAMENTO CTE ON CTE.ID_BD_TIPO_EQUIPAMENTO = IEQ.ID_BD_TIPO_NE AND CTE.SIGLA = 'OLT' " +
                    "LEFT JOIN NS_RES_INS_TP_TP TP_TP2 ON TP_AVO.ID = TP_TP2.ID_TP_PARENT " +
                    "LEFT JOIN NS_RES_INS_TP TP_PAI ON TP_TP2.ID_TP = TP_PAI.ID " +
                    "LEFT JOIN NS_RES_INS_TP_TP TP_TP1 ON TP_PAI.ID = TP_TP1.ID_TP_PARENT " +
                    "LEFT JOIN NS_RES_INS_TP TP ON TP_TP1.ID_TP = TP.ID AND TP.NAME = TO_CHAR(:ontId)  " +
                    "WHERE IEQ.ID_BD_EQUIPAMENTO = :idOltIsp AND PF.CODIFICACAO_PORTO = :portCode " +
                    "GROUP BY TP_AVO.ID";

    private static void addParameters(Long ontId, Long idOltIsp, String portCode, MapSqlParameterSource parameters) {
        if (ontId == null) {
            throw new IllegalArgumentException("ONT ID must not be null or empty");
        }
        if (portCode == null || portCode.isEmpty()) {
            throw new IllegalArgumentException("Port Code must not be null or empty");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters source must not be null");
        }
        parameters.addValue("ontId", ontId);
        parameters.addValue("idOltIsp", idOltIsp);
        parameters.addValue("portCode", portCode);
    }

    public String getFindPortByOltQuery(Long ontId, Long idOltIsp, String portCode, MapSqlParameterSource parameters) {
        addParameters(ontId, idOltIsp, portCode, parameters);
        return QUERY_BASE;
    }
}
