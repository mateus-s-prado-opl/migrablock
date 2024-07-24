package com.ws.ont.enums;

import com.ws.cvlan.SolEnum;

public enum OltAttributesAttr implements SolEnum {

    OLT_PTP_ID("OLT_PTP_ID"),
    OLT_PTP_NAME("OLT_PTP_NAME"),
    OLT_TTP_ID("OLT_TTP_ID"),
    OLT_CTP_ID("OLT_CTP_ID"),
    OLT_CTP_NAME("OLT_CTP_NAME");

    private final String fieldName;

    OltAttributesAttr(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
