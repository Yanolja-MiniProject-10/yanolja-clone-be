package com.ybe.mp10.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeUtil {
    public static LocalDateTime toDateTime(LocalDate date) {
        return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0);
    }


    public static LocalDate MIN(LocalDate d1, LocalDate d2) {
        if (d1.isAfter(d2)) {
            return d2;
        }
        return d1;
    }

    public static LocalDate MAX(LocalDate d1, LocalDate d2) {
        if (d1.isAfter(d2)) {
            return d1;
        }
        return d2;
    }
}
