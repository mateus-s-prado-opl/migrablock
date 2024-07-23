package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class FindOltByNameSql {

    private static final String QUERY_BASE =
            "SELECT IEQ.ID_BD_EQUIPAMENTO AS ID_OLT_ISP, NOMI.ID_NODE AS ID_OLT_NS " +
                    "FROM ISP_INS_EQUIPAMENTO IEQ " +
                    "INNER JOIN ISP_CAT_TIPO_EQUIPAMENTO CTE " +
                    "ON CTE.ID_BD_TIPO_EQUIPAMENTO = IEQ.ID_BD_TIPO_NE AND CTE.SIGLA = 'OLT' " +
                    "INNER JOIN NS_RES_INS_NODE_MIRROR NOMI " +
                    "ON NOMI.ID_ISP = IEQ.ID_BD_EQUIPAMENTO AND NOMI.ENTITY_ISP = 'AC_GEN_INS_EQUIPAMENTO' " +
                    "WHERE IEQ.IDENTIFICACAO = :oltName";


    private static void addOltNameParameter(String oltName, MapSqlParameterSource parameters) {
        if (oltName == null || oltName.isEmpty()) {
            throw new IllegalArgumentException("OLT name must not be null or empty");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters source must not be null");
        }
        parameters.addValue("oltName", oltName);
    }

    public String getFindOltByNameQuery(String oltName, MapSqlParameterSource parameters) {
        addOltNameParameter(oltName, parameters);
        return QUERY_BASE;
    }
}
