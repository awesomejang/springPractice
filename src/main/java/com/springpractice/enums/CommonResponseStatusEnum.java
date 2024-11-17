package com.springpractice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CommonResponseStatusEnum {
    SUCCESS("success"),
    FAIL("fail");

    private final String status;

    CommonResponseStatusEnum(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
