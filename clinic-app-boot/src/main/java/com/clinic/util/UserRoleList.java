package com.clinic.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRoleList {

    USER(1),
    DOCTOR(2);

    private final int id;
}
