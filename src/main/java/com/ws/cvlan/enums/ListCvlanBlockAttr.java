package com.ws.cvlan.enums;

import com.ws.cvlan.SolEnum;

public enum ListCvlanBlockAttr implements SolEnum {

    PROCESS_ID("PROCESS_ID"),
    USER_CREATED("USER_CREATED"),
    DATE_CREATED("DATE_CREATED"),
    STATE_ABBREVIATION("CTL_UF"),
    STATE_NAME("CTL_NAME"),
    LOCALITY_ABBREVIATION("CTL_CIDADE_SIGLA"),
    LOCALITY_NAME("CTL_CIDADE"),
    OLT_NAME("OLT"),
    OLT_UID("UID_OLT"),
    PON_INTERFACE("INTERFACE_PON"),
    ONT_ID("ONT_ID"),
    SVLAN("SVLAN"),
    CVLAN("CVLAN"),
    COMMENTS("COMMENTS");

    private final String fieldName;

    ListCvlanBlockAttr(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return this.name() + ": " + this.fieldName;
    }

}
