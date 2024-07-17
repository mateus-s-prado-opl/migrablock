package com.ws.ont.enums;

import com.ws.cvlan.SolEnum;

public enum ListOntBlockAttr implements SolEnum {

    PROCESS_ID("PROCESS_ID"),
    USER_CREATED("USER_CREATED"),
    DATE_CREATED("DATE_CREATED"),
    STATE_ABBREVIATION("UF"),
    STATE_NAME("UF_NAME"),
    LOCALITY_ABBREVIATION("CIDADE_SIGLA"),
    LOCALITY_NAME("CIDADE"),
    OLT_NAME("OLT"),
    OLT_UID("UID_OLT"),
    PON_INTERFACE("INTERFACE_PON"),
    ONT_ID("ONT_ID");

    private final String fieldName;

    ListOntBlockAttr(String fieldName) {
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
