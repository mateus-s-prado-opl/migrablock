package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.enums.StatusCVLAN;
import com.ws.cvlan.filter.ListCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class ListCvlanBlocksSql {

    private static final StringBuilder QUERY_BASE =
            new StringBuilder()
                    .append("SELECT DISTINCT ")
                    .append("  ALMB.PROCESS_ID, ALMB.USER_CREATED, ALMB.DATE_CREATED, ALMB.COMMENTS, ")
                    .append("  CB.UID_OLT, CB.CTL_NAME, CB.CTL_UF, CB.CTL_CIDADE, ")
                    .append("  CB.CTL_CIDADE_SIGLA, CB.OLT, CB.ONT_ID, CB.SVLAN, ")
                    .append("  CB.CVLAN, CB.INTERFACE_PON ")
                    .append("FROM MIG_BLOCKED_CVLAN CB ")
                    .append("INNER JOIN AUDIT_LOG_MIG_BLOCK_CVLAN ALMB ON ALMB.PROCESS_ID = CB.ID ")
                    .append("WHERE CB.IS_BLOCKED = :isBlocked ")
                    .append("  AND ALMB.DATE_CREATED = ( ")
                    .append("      SELECT MAX(ALMB_SUB.DATE_CREATED) ")
                    .append("      FROM AUDIT_LOG_MIG_BLOCK_CVLAN ALMB_SUB ")
                    .append("      WHERE ALMB_SUB.PROCESS_ID = CB.ID ")
                    .append("  )");

    public static String getQueryListCvlanBlock(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(QUERY_BASE);
        addWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }

    private static void addWhere(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {

        namedParameters.addValue("isBlocked", StatusCVLAN.BLOCKED.getValue());

        if (filter.getSvlan() != null) {
            finalQuery.append(" AND CB.svlan = :svlan ");
            namedParameters.addValue("svlan", filter.getSvlan());
        }

        if (filter.getCvlan() != null) {
            finalQuery.append(" AND CB.cvlan = :cvlan ");
            namedParameters.addValue("cvlan", filter.getCvlan());
        }

        if (filter.getStateAbbreviation() != null) {
            finalQuery.append(" AND CB.CTL_UF = :siglaUf ");
            namedParameters.addValue("siglaUf", filter.getStateAbbreviation());
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
}
