package com.ws.cvlan.sql;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class CvlanSql {

    private static final StringBuilder queryInsertBlockedCvlan = new StringBuilder()
            .append("INSERT INTO MIG_BLOCKED_CVLAN (\n")
            .append("    SERVICE_PROVIDER, SVLAN_PROFILE, CVLAN, SVLAN,\n")
            .append("    ID_OLT, OLT, ONT_ID, CTO, CTL_CIDADE, CTL_UF\n")
            .append(") \n")
            .append("SELECT \n")
            .append("    'VIVO', \n")
            .append("    'Broadband', \n")
            .append("    :cvlan, \n")
            .append("    :svlan, \n")
            .append("    :oltId, \n")
            .append("    :olt, \n")
            .append("    :ontId, \n")
            .append("    'TEST', \n")
            .append("    :localityName, \n")
            .append("    :localityAbbreviation \n")
            .append("FROM DUAL \n")
            .append("WHERE NOT EXISTS (\n")
            .append("    SELECT 1 \n")
            .append("    FROM MIG_BLOCKED_CVLAN \n")
            .append("    WHERE CVLAN = 1 \n")
            .append(") ");

    private static void addWhere(AddCvlanBlockFilter addCvlanFilter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {

        //TODO: ServiceProvider

        //TODO: svlanProfile

        if (addCvlanFilter.getCvlan() != null) {
            namedParameters.addValue("cvlan", addCvlanFilter.getCvlan());
        }

        if (addCvlanFilter.getSvlan() != null) {
            namedParameters.addValue("svlan", addCvlanFilter.getSvlan());
        }

        if (addCvlanFilter.getOltUid() != null) {
            namedParameters.addValue("oltId", Long.parseLong(addCvlanFilter.getOltUid()));
        }

        if (addCvlanFilter.getOltName() != null) {
            namedParameters.addValue("olt", addCvlanFilter.getOltName());
        }

        if (addCvlanFilter.getOntId() != null) {
            namedParameters.addValue("ontId", addCvlanFilter.getOntId());
        }

        //TODO: CTO

        if (addCvlanFilter.getLocalityName() != null) {
            namedParameters.addValue("localityName", addCvlanFilter.getLocalityName());
        }

        if (addCvlanFilter.getLocalityAbbreviation() != null) {
            namedParameters.addValue("localityAbbreviation", addCvlanFilter.getLocalityAbbreviation());
        }
    }

    public static String getQueryAddCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(queryInsertBlockedCvlan);
        addWhere(addCvlanBlockFilter, namedParameters, finalQuery);
        return finalQuery.toString();
    }
}
