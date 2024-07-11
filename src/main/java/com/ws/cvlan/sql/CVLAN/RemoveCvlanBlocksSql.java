package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.enums.StatusCVLAN;
import com.ws.cvlan.filter.RemoveCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class RemoveCvlanBlocksSql {

    private static final StringBuilder QUERY_REMOVE_CVLAN_BLOCKS =
            new StringBuilder()
                    .append(" UPDATE MIG_BLOCKED_CVLAN CB SET IS_BLOCKED = :isNotBlocked ")
                    .append(" WHERE 1=1 ");

    private static void addWhere(RemoveCvlanBlockFilter filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {

        namedParameters.addValue("isNotBlocked", StatusCVLAN.NOT_BLOCKED.getValue());

        if (filter.getSvlan() != null) {
            finalQuery.append(" AND CB.svlan = :svlan ");
            namedParameters.addValue("svlan", filter.getSvlan());
        }

        if (filter.getCvlan() != null) {
            finalQuery.append(" AND CB.cvlan = :cvlan ");
            namedParameters.addValue("cvlan", filter.getCvlan());
        }

        if (filter.getStateAbbreviation() != null) {
            finalQuery.append(" AND CB.CTL_UF = :stateAbbreviation ");
            namedParameters.addValue("stateAbbreviation", filter.getStateAbbreviation());
        }

        if (filter.getStateName() != null) {
            finalQuery.append(" AND CB.CTL_NAME = :stateName ");
            namedParameters.addValue("stateName", filter.getStateName());
        }

        if (filter.getLocalityAbbreviation() != null) {
            finalQuery.append(" AND CB.CTL_CIDADE_SIGLA = :localityAbbreviation ");
            namedParameters.addValue("localityAbbreviation", filter.getLocalityAbbreviation());
        }

        if (filter.getLocalityName() != null) {
            finalQuery.append(" AND CB.CTL_CIDADE = :localityName ");
            namedParameters.addValue("localityName", filter.getLocalityName());
        }

        if (filter.getPonInterface() != null) {
            finalQuery.append(" AND CB.INTERFACE_PON = :ponInterface ");
            namedParameters.addValue("ponInterface", filter.getPonInterface());
        }

        if (filter.getOltName() != null) {
            finalQuery.append(" AND CB.OLT = :OLT ");
            namedParameters.addValue("OLT", filter.getOltName());
        }

        if (filter.getOltUid() != null) {
            finalQuery.append(" AND CB.UID_OLT = :UID_OLT ");
            namedParameters.addValue("UID_OLT", filter.getOltUid());
        }

        if (filter.getOntId() != null) {
            finalQuery.append(" AND CB.ONT_ID = :ONT_ID ");
            namedParameters.addValue("ONT_ID", filter.getOntId());
        }
    }

    public static String getQueryRemoveCvlanBlock(RemoveCvlanBlockFilter filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(QUERY_REMOVE_CVLAN_BLOCKS);
        addWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }
}
