package com.ws.utils.enums;

public enum Operation {

    BLOCK_CVLAN("bloqueio cvlan"),
    UNBLOCK_CVLAN("desbloqueio cvlan"),
    BLOCK_ONT("bloqueio ont"),
    UNBLOCK_ONT("desbloqueio ont");

    private final String description;

    Operation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
