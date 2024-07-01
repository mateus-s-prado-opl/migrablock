package com.ws.cvlan.enums;

public enum Operation {

    BLOCK_CVLAN("bloqueio cvlan");

    private final String description;

    Operation(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
