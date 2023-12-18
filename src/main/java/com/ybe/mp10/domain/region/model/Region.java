package com.ybe.mp10.domain.region.model;

import com.ybe.mp10.domain.region.exception.RegionException;
import lombok.Getter;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum Region {
    SEOUL(1L, "서울"), GANGWON(2L,"강원"), GYEONGGI(3L, "경기"), JEJU(4L, "제주");
    private final Long id;
    private final String value;

    Region(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public static Region fromValue(String value) {
        return Arrays.stream(Region.values())
                .filter(v -> v.getValue().equals(value))
                .findAny().orElseThrow(() -> new RegionException(value+" 존재하지 않는 지역입니다.", NOT_FOUND));
    }


}
