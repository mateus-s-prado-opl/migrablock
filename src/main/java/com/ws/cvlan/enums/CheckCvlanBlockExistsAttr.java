package com.ws.cvlan.enums;

import com.ws.utils.SolEnum;

public enum CheckCvlanBlockExistsAttr implements SolEnum {

    PROCESS_ID("PROCESS_ID"),
    IS_BLOCKED("IS_BLOCKED"),
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
