package com.ybe.mp10.domain.category.model;

import com.ybe.mp10.domain.category.exception.CategoryException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum Category {
    HOTEL_RESORT(1L, "호텔_리조트"), PENSION_VILLA(2L, "펜션_풀빌라"), MOTEL(3L, "모텔");
    private final Long id;
    private final String value;

    Category(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public static Category fromValue(String value) {
        return Arrays.stream(Category.values())
                .filter(v -> v.getValue().equals(value))
                .findAny().orElseThrow(() -> new CategoryException(value+" 존재하지 않는 카테고리입니다.", NOT_FOUND));
    }

}
