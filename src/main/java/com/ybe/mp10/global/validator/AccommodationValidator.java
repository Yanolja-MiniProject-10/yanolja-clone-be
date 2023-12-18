package com.ybe.mp10.global.validator;

import com.ybe.mp10.domain.accommodation.exception.AccommodationException;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.CONFLICT;

public class AccommodationValidator {
    public static void validateStartDateAndEndDateAndGuest(LocalDate startDate, LocalDate endDate, Long guest) {
        if (startDate.isAfter(endDate)) {
            throw new AccommodationException("숙박 종료일은 숙박 시작일 이후여야합니다", CONFLICT);
        }
        if (guest == 0) {
            throw new AccommodationException("숙박 인원수는 1 이상이어야합니다.", CONFLICT);
        }
    }
}
