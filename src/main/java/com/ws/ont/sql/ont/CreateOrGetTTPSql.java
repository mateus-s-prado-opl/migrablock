package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

public class CreateOrGetTTPSql {
    private final SimpleJdbcCall jdbcCall;

    public CreateOrGetTTPSql(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName("PCK_NETWORK_OSS")  // Nome do pacote
                .withProcedureName("CREATE_OR_GET_TTP")  // Nome do procedimento
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_ENTITY_TP_OSS", Types.NUMERIC), // Parâmetro de entrada
                        new SqlParameter("P_NOME_TTP", Types.VARCHAR), // Parâmetro de entrada
                        new SqlParameter("P_TYPE_TTP", Types.VARCHAR), // Parâmetro de entrada


                        new SqlOutParameter("P_ID_ENTITY_TTP_OSS", Types.NUMERIC), // Parâmetro de saída
                        new SqlOutParameter("OUTOutputCode", Types.INTEGER), // Parâmetro de saída
                        new SqlOutParameter("OUTCodeDescription", Types.VARCHAR) // Parâmetro de saída
                );
    }

    public Map<String, Object> execute(Long idEntityTpOss, String nomeTtp, String typeTtp) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ID_ENTITY_TP_OSS", idEntityTpOss)
                .addValue("P_NOME_TTP", nomeTtp)
                .addValue("P_TYPE_TTP", typeTtp);

        return jdbcCall.execute(params);
    }
}
