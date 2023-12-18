package com.ybe.mp10.global.common.enums;

import lombok.Getter;

@Getter
public enum ImageType {
    ACCOMMODATION_THUMB_NAIL("ACCOMMODATION_THUMB_NAIL"), ACCOMMODATION_MAIN("ACCOMMODATION_MAIN"), ROOM_OPTION_THUMBNAIL("ROOM_OPTION_THUMBNAIL"), ROOM_OPTION_MAIN("ROOM_OPTION_MAIN");
    private final String value;

    ImageType(String value) {
        this.value = value;
    }
}
