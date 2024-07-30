package com.ws.utils.enums;

public enum Status {
    ERROR(0L),
    SUCCESS(1L);

    private final long value;

    Status(long l) {
        value = l;
    }

    public long getValue() {
        return value;
    }
}
