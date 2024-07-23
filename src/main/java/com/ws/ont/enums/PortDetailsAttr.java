package com.ws.ont.enums;

import com.ws.cvlan.SolEnum;

public enum PortDetailsAttr implements SolEnum {


    OLT_PTP_ID("olt_ptp_ID"),
    OLT_CTP_ID("olt_ctp_ID"),
    USER_CREATE("USER_CREATE");

    private final String fieldName;

    PortDetailsAttr(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
