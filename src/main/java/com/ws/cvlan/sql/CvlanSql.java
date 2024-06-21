package com.ws.cvlan.sql;

import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.filter.RemoveCvlanBlockFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class CvlanSql {

    private static final StringBuilder addCvlanBlock = new StringBuilder();

    private static final StringBuilder removeCvlanBlock = new StringBuilder();

    public static String getQueryAddCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter, MapSqlParameterSource namedParameters) {



        return "";
    }

    public static String getQueryRemoveCvlanBlock(RemoveCvlanBlockFilter removeCvlanBlockFilter, MapSqlParameterSource namedParameters) {
        return "";
    }
}
