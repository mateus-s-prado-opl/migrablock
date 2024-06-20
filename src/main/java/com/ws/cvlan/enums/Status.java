package com.ws.cvlan.enums;

public enum Status {
    ERROR(0L),
    SUCCESS(1L);

    private long value;

    Status(long l) {
        value = l;
    }

    public long getValue() {
        return value;
    }
}
