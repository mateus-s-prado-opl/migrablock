package com.ws.cvlan.enums;

public enum StatusCVLAN {

    NOT_BLOCKED(0L),
    BLOCKED(1L);

    private final long value;

    StatusCVLAN(long l) {
        value = l;
    }

    public long getValue() {
        return value;
    }

}
