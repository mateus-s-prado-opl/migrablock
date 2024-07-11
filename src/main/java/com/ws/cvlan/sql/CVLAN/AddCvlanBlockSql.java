package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class AddCvlanBlockSql {

    private static final String SERVICE_PROVIDER = "";
    private static final String SVLAN_PROFILE = "Broadband";
    private static final String CTO = "";

    private static final StringBuilder QUERY_INSERT_BLOCKED_CVLAN = new StringBuilder()
            .append("INSERT INTO MIG_BLOCKED_CVLAN (")
            .append("    SERVICE_PROVIDER, SVLAN_PROFILE, CVLAN, SVLAN, UID_OLT, OLT, ONT_ID, CTO, CTL_CIDADE_SIGLA, ")
            .append("    CTL_CIDADE, CTL_UF, CTL_NAME, INTERFACE_PON")
            .append(") VALUES (")
            .append("    :serviceProvider, :svlanProfile, :cvlan, :svlan, :oltUid, :olt, :ontId, :cto, ")
            .append("    :localityAbbreviation, :localityName, :stateAbbreviation, :stateName, :ponInterface")
            .append(")");

    public static String getQueryAddCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter, MapSqlParameterSource namedParameters) {
        String finalQuery = QUERY_INSERT_BLOCKED_CVLAN.toString();
        addValues(addCvlanBlockFilter, namedParameters);
        return finalQuery;
    }

    private static void addValues(AddCvlanBlockFilter addCvlanFilter, MapSqlParameterSource namedParameters) {

        namedParameters.addValue("serviceProvider", SERVICE_PROVIDER);
        namedParameters.addValue("svlanProfile", SVLAN_PROFILE);
        namedParameters.addValue("cto", CTO);

        namedParameters.addValue("cvlan", addCvlanFilter.getCvlan());
        namedParameters.addValue("svlan", addCvlanFilter.getSvlan());
        namedParameters.addValue("oltUid", addCvlanFilter.getOltUid());
        namedParameters.addValue("olt", addCvlanFilter.getOltName());
        namedParameters.addValue("ontId", addCvlanFilter.getOntId());
        namedParameters.addValue("localityAbbreviation", addCvlanFilter.getLocalityAbbreviation());
        namedParameters.addValue("localityName", addCvlanFilter.getLocalityName());
        namedParameters.addValue("stateAbbreviation", addCvlanFilter.getStateAbbreviation());
        namedParameters.addValue("stateName", addCvlanFilter.getStateName());
        namedParameters.addValue("ponInterface", addCvlanFilter.getPonInterface());
    }
}
