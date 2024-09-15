package com.interview.enums;

import lombok.Getter;

@Getter
public enum UserType {
    DEFAULT_SUPERVISOR_USER("default-supervisor-user"),
    DEFAULT_ADMIN_USER("default-admin-user");

    private final String value;

    UserType(String value) {
        this.value = value;
    }
}