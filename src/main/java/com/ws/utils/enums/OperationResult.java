package com.ws.utils.enums;

public enum OperationResult {
    SUCCESS("Operation successful", Status.SUCCESS),
    CVLAN_BLOCKED("CLAN is already blocked.", Status.SUCCESS),
    CVLAN_NOT_FOUND("CVLAN not found", Status.ERROR),
    OLT_NOT_FOUND("OLT not found", Status.ERROR),
    PORT_NOT_FOUND("PORT not found", Status.ERROR),
    OLT_ALREADY_CREATED("Olt already created", Status.ERROR),
    BLOCK_NOT_FOUND("Block not found", Status.ERROR),
    UNKNOWN_ERROR("An unexpected error occurred. Please contact support for assistance.", Status.ERROR);

    private final String message;
    private final Status status;

    OperationResult(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

}
