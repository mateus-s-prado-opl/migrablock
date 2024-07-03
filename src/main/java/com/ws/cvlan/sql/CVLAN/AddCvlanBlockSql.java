package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class AddCvlanBlockSql {

    private static final StringBuilder queryInsertBlockedCvlan = new StringBuilder()
            .append("INSERT INTO MIG_BLOCKED_CVLAN (")
            .append("    SERVICE_PROVIDER, SVLAN_PROFILE, CVLAN, SVLAN,")
            .append("    UID_OLT, OLT, ONT_ID, CTO, CTL_CIDADE_SIGLA, CTL_CIDADE, CTL_UF, CTL_NAME, INTERFACE_PON")
            .append(") ")
            .append("VALUES (")
            .append("    '', ")
            .append("    'Broadband', ")
            .append("    :cvlan, ")
            .append("    :svlan, ")
            .append("    :oltUid, ")
            .append("    :olt, ")
            .append("    :ontId, ")
            .append("    '', ")
            .append("    :localityAbbreviation, ")
            .append("    :localityName, ")
            .append("    :stateAbbreviation, ")
            .append("    :stateName, ")
            .append("    :ponInterface ")
            .append(")");


    private static void addValues(AddCvlanBlockFilter addCvlanFilter, MapSqlParameterSource namedParameters) {

        if (addCvlanFilter.getCvlan() != null) {
            namedParameters.addValue("cvlan", addCvlanFilter.getCvlan());
        }

        if (addCvlanFilter.getSvlan() != null) {
            namedParameters.addValue("svlan", addCvlanFilter.getSvlan());
        }

        if (addCvlanFilter.getOltUid() != null) {
            namedParameters.addValue("oltUid", addCvlanFilter.getOltUid());
        }

        if (addCvlanFilter.getOltName() != null) {
            namedParameters.addValue("olt", addCvlanFilter.getOltName());
        }

        if (addCvlanFilter.getOntId() != null) {
            namedParameters.addValue("ontId", addCvlanFilter.getOntId());
        }

        if (addCvlanFilter.getLocalityAbbreviation() != null) {
            namedParameters.addValue("localityAbbreviation", addCvlanFilter.getLocalityAbbreviation());
        }

        if (addCvlanFilter.getLocalityName() != null) {
            namedParameters.addValue("localityName", addCvlanFilter.getLocalityName());
        }

        if (addCvlanFilter.getStateAbbreviation() != null) {
            namedParameters.addValue("stateAbbreviation", addCvlanFilter.getStateAbbreviation());
        }

        if (addCvlanFilter.getStateName() != null) {
            namedParameters.addValue("stateName", addCvlanFilter.getStateName());
        }

        if (addCvlanFilter.getPonInterface() != null) {
            namedParameters.addValue("ponInterface", addCvlanFilter.getPonInterface());
        }

    }

    public static String getQueryAddCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter, MapSqlParameterSource namedParameters) {
        String finalQuery = String.valueOf(queryInsertBlockedCvlan);
        addValues(addCvlanBlockFilter, namedParameters);
        return finalQuery;
    }
}
