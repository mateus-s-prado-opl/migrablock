package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class AddCvlanBlockSql {

    private static final StringBuilder queryInsertBlockedCvlan = new StringBuilder()
            .append("INSERT INTO MIG_BLOCKED_CVLAN (")
            .append("    SERVICE_PROVIDER, SVLAN_PROFILE, CVLAN, SVLAN,")
            .append("    ID_OLT, OLT, ONT_ID, CTO, CTL_CIDADE, CTL_UF")
            .append(") ")
            .append("VALUES (")
            .append("    '', ")
            .append("    'Broadband', ")
            .append("    :cvlan, ")
            .append("    :svlan, ")
            .append("    :oltId, ")
            .append("    :olt, ")
            .append("    :ontId, ")
            .append("    '', ")
            .append("    :localityName, ")
            .append("    :localityAbbreviation ")
            .append(")");


    private static void addValues(AddCvlanBlockFilter addCvlanFilter, MapSqlParameterSource namedParameters) {

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


        if (addCvlanFilter.getLocalityName() != null) {
            namedParameters.addValue("localityName", addCvlanFilter.getLocalityName());
        }

        if (addCvlanFilter.getLocalityAbbreviation() != null) {
            namedParameters.addValue("localityAbbreviation", addCvlanFilter.getLocalityAbbreviation());
        }
    }

    public static String getQueryAddCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter, MapSqlParameterSource namedParameters) {
        String finalQuery = String.valueOf(queryInsertBlockedCvlan);
        addValues(addCvlanBlockFilter, namedParameters);
        return finalQuery;
    }
}
