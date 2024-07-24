package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

public class CreateOrGetTTPSql {
    private final SimpleJdbcCall jdbcCall;

    public CreateOrGetTTPSql(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withSchemaName("NETWIN").withCatalogName("PCK_NETWORK_OSS")
                .withProcedureName("CREATE_OR_GET_TTP")
                .declareParameters(
                        new SqlOutParameter("P_ID_ENTITY_TP_OSS", Types.NUMERIC),
                        new SqlOutParameter("OUTOUTPUTCODE", Types.INTEGER),
                        new SqlOutParameter("OUTCODEDESCRIPTION", Types.VARCHAR)
                );
    }

    public Map<String, Object> execute(Long idEntityTpOss, String nomeTtp) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ID_ENTITY_TP_OSS", idEntityTpOss)
                .addValue("P_NOME_TTP", nomeTtp)
                .addValue("P_TYPE_TTP", "TTP.MULTI");

        return jdbcCall.execute(params);
    }
}
