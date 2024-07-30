package com.ws.ont.enums;

import com.ws.utils.SolEnum;

public enum OltIdsAttr implements SolEnum  {

    ID_OLT_ISP("ID_OLT_ISP"),
    ID_OLT_NS("ID_OLT_NS");

    private final String fieldName;

    OltIdsAttr(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
