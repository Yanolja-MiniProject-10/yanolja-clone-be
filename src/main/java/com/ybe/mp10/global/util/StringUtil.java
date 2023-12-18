package com.ybe.mp10.global.util;

import java.time.LocalDate;

public class StringUtil {
    public static LocalDate fromPlainStringToLocalDate(String givenString) {
        return LocalDate.of(Integer.parseInt(givenString.substring(0, 4)), Integer.parseInt(givenString.substring(4, 6)), Integer.parseInt(givenString.substring(6)));
    }
}
