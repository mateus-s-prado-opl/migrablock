package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class CheckCvlanBlockExistsSql {

    private static final StringBuilder QUERY_BASE =
            new StringBuilder()
                    .append(" SELECT ALMB.PROCESS_ID, ALMB.USER_CREATED, ALMB.DATE_CREATED, ALMB.COMMENTS, CB.IS_BLOCKED ")
                    .append(" FROM MIG_BLOCKED_CVLAN CB ")
                    .append(" INNER JOIN AUDIT_LOG_MIG_BLOCK_CVLAN ALMB ON ALMB.PROCESS_ID = CB.ID  ")
                    .append(" WHERE 1=1 ");


    public static String getQueryCheckCvlanBlockExists(CheckCvlanBlockExistsDTO filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(QUERY_BASE);
        addBaseWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }

    private static void addBaseWhere(CheckCvlanBlockExistsDTO filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {
        if (filter.getSvlan() != null) {
            finalQuery.append(" AND CB.svlan = :svlan ");
            namedParameters.addValue("svlan", filter.getSvlan());
        }
        if (filter.getCvlan() != null) {
            finalQuery.append(" AND CB.cvlan = :cvlan ");
            namedParameters.addValue("cvlan", filter.getCvlan());
        }
    }


    public static String getQueryCheckCvlanBlockExistsForUnblock(CheckCvlanBlockExistsDTO filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(QUERY_BASE);
        addBaseWhere(filter, namedParameters, finalQuery);
        addUnblockWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }

    private static void addUnblockWhere(CheckCvlanBlockExistsDTO filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {
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

}
