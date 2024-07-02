package com.ws.cvlan.sql.CVLAN;

import com.ws.cvlan.filter.ListCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class ListCvlanBlocksSql {

    private static final StringBuilder queryCheckCvlanBlockExists =
            new StringBuilder()
                    .append(" SELECT ALMB.PROCESS_ID, ALMB.USER_CREATED, ALMB.DATE_CREATED, ALMB.COMMENTS, ")
                    .append(" CB.CTL_UF, CB.CTL_CIDADE, CB.OLT, CB.ONT_ID, CB.SVLAN, CB.CVLAN ")
                    .append(" FROM MIG_BLOCKED_CVLAN CB ")
                    .append(" INNER JOIN AUDIT_LOG_MIG_BLOCK_CVLAN ALMB ON ALMB.PROCESS_ID = CB.ID  ")
                    .append(" WHERE 1=1 ");

    private static void addWhere(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters, StringBuilder finalQuery) {


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

//        if (filter.getNomeUf() != null) {
//            finalQuery.append(" AND CB.nome_uf = :nomeUf ");
//            namedParameters.addValue("nomeUf", filter.getNomeUf());
//        }

//
//        if (filter.getLocalityAbbreviation() != null) {
//            finalQuery.append(" AND CB.CTL_CIDADE = :siglaLocalidade ");
//            namedParameters.addValue("siglaLocalidade", filter.getLocalityAbbreviation());
//        }

        if (filter.getLocalityName() != null) {
            finalQuery.append(" AND CB.CTL_CIDADE = :CTL_CIDADE ");
            namedParameters.addValue("CTL_CIDADE", filter.getLocalityName());
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

    public static String getQueryListCvlanBlock(ListCvlanBlockFilter filter, MapSqlParameterSource namedParameters) {
        StringBuilder finalQuery = new StringBuilder(queryCheckCvlanBlockExists);
        addWhere(filter, namedParameters, finalQuery);
        return finalQuery.toString();
    }
}
