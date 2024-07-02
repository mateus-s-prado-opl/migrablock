package com.ws.cvlan.util;

import com.ws.cvlan.SolEnum;

import java.util.Map;

public class StringUtilSol {

    public static String getString(Map<String, Object> tuple, SolEnum attrName) {
        Object objValue = tuple.get(attrName.getFieldName());
        String value = "";
        if (tuple.get(attrName.getFieldName()) != null)
            value = String.valueOf(objValue);

        return value;
    }

    public static Long getLong(Map<String, Object> tuple, SolEnum attrName) {
        Object objValue = tuple.get(attrName.getFieldName());
        Long value = null;
        if (tuple.get(attrName.getFieldName()) != null)
            value = Long.valueOf(objValue.toString());

        return value;
    }

    public static Boolean getBoolean(Map<String, Object> tuple, SolEnum attrName) {
        Object objValue = tuple.get(attrName.getFieldName());
        Boolean value = null;
        if (tuple.get(attrName.getFieldName()) != null)
            value = Boolean.valueOf(objValue.toString());

        return value;
    }
}
