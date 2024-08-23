package com.springpractice.dao.enums;

import java.util.Map;

public enum AuthStatusEnum {
    ACTIVE,
    INACTIVE;

    private static final Map<String, AuthStatusEnum> STRING_TO_ENUM_MAP = Map.of(
            "ACTIVE", ACTIVE,
            "INACTIVE", INACTIVE
    );

    public static AuthStatusEnum fromString(String value) {
        AuthStatusEnum authStatusEnum = STRING_TO_ENUM_MAP.get(value.toUpperCase());

        if(authStatusEnum == null) {
            throw new IllegalArgumentException("No such enum constant " + value);
        }
        return authStatusEnum;
    }
}
