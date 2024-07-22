package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class RemoveOntBlocksSql {

    private static final String DELETE_NS_RES_INS_TP_TP = "DELETE FROM NS_RES_INS_TP_TP WHERE ID_TP = :ID";
    private static final String DELETE_NS_RES_INS_LTP_NODE = "DELETE FROM NS_RES_INS_LTP_NODE WHERE ID_TP = :ID";
    private static final String DELETE_NS_RES_INS_TP = "DELETE FROM NS_RES_INS_TP WHERE ID = :ID";

    private static void addWhere(Long idBlock, MapSqlParameterSource namedParameters) {
        namedParameters.addValue("ID", idBlock);
    }

    public static void executeRemoveOntBlock(NamedParameterJdbcTemplate jdbcTemplate, Long idBlock) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        addWhere(idBlock, namedParameters);

        jdbcTemplate.update(DELETE_NS_RES_INS_TP_TP, namedParameters);
        jdbcTemplate.update(DELETE_NS_RES_INS_LTP_NODE, namedParameters);
        jdbcTemplate.update(DELETE_NS_RES_INS_TP, namedParameters);
    }
}
