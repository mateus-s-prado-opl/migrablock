package com.ws.cvlan.enums;

import com.ws.cvlan.SolEnum;

public enum CheckCvlanBlockExistsAttr implements SolEnum {

    PROCESS_ID("PROCESS_ID"),
    USER("USER_CREATED"),
    DATE_CREATED("DATE_CREATED"),
    COMMENTS("COMMENTS");

    private final String description;

    CheckCvlanBlockExistsAttr(String description) {
        this.description = description;
    }

    @Override
    public String getFieldName() {
        return description;
    }

    @Override
    public String toString() {
        return this.name() + ": " + this.description;
    }


}
