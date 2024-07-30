package com.ws.utils;

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
}
