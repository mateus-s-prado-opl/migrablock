package com.ws.ont.sql.ont;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class UpdateNsResInsTpSql {
    private static final String QUERY_UPDATE =
            "UPDATE NS_RES_INS_TP " +
                    "SET USER_CREATE = :userCreate, " +
                    "    ADMINISTRATIVE_STATE_DATE = SYSDATE, " +
                    "    ADMINISTRATIVE_STATE_ID = (" +
                    "        SELECT CS.ID " +
                    "        FROM CAT_STATE CS " +
                    "        INNER JOIN CAT_STATE_MACHINE CSM ON CSM.ID = CS.ID_STATE_MACHINE_OWNER " +
                    "        WHERE CS.NAME = 'OCCUPIED' AND CSM.NAME = 'SM_ADMINISTRATIVE_STATE'" +
                    "    ) " +
                    "WHERE ID = :ttpId";

    private static void addUpdateParameters(String userCreate, Long ttpId, MapSqlParameterSource parameters) {
        if (userCreate == null || userCreate.isEmpty()) {
            throw new IllegalArgumentException("User create must not be null or empty");
        }
        if (ttpId == null) {
            throw new IllegalArgumentException("TTP ID must not be null");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters source must not be null");
        }
        parameters.addValue("userCreate", userCreate);
        parameters.addValue("ttpId", ttpId);
    }

    public String getUpdateNsResInsTpQuery(String userCreate, Long ttpId, MapSqlParameterSource parameters) {
        addUpdateParameters(userCreate, ttpId, parameters);
        return QUERY_UPDATE;
    }
}
