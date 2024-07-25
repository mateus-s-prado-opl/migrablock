package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

public class CreateOrGetCTPSql {
    private final SimpleJdbcCall jdbcCall;

    public CreateOrGetCTPSql(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName("PCK_NETWORK_OSS")  // Nome do pacote
                .withProcedureName("Create_or_Get_CTP")  // Nome do procedimento
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_ENTITY_TP_OSS", Types.NUMERIC), // Parâmetro de entrada
                        new SqlParameter("P_CTP_NAME", Types.VARCHAR), // Parâmetro de entrada
                        new SqlParameter("P_CTP_TYPE", Types.VARCHAR), // Parâmetro de entrada


                        new SqlOutParameter("P_ID_ENTITY_CTP_OSS", Types.BIGINT), // Parâmetro de saída
                        new SqlOutParameter("OUTOUTPUTCODE", Types.INTEGER), // Parâmetro de saída
                        new SqlOutParameter("OUTCODEDESCRIPTION", Types.VARCHAR) // Parâmetro de saída
                );
    }

    public Map<String, Object> execute(Long vTtpId, Long ontId, String typeTtp) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ID_ENTITY_TP_OSS", vTtpId)
                .addValue("P_CTP_NAME", String.valueOf(ontId))
                .addValue("P_CTP_TYPE", typeTtp);

        return jdbcCall.execute(params);
    }
}
