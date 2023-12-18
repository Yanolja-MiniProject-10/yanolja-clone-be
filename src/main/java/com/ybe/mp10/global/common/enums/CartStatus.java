package com.ybe.mp10.global.common.enums;

import lombok.Getter;

@Getter
public enum CartStatus {
    NONE("NONE")
//    , PENDING("PENDING")
    , PAID("PAID")
    , INSTANT ("INSTANT");
    private final String value;

    CartStatus(String value) {
        this.value = value;
    }
}
