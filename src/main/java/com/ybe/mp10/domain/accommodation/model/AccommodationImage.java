package com.ybe.mp10.domain.accommodation.model;

import com.ybe.mp10.global.converter.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationImage {
    private List<String> mainImageUrls;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccommodationImage that = (AccommodationImage) o;
        return Objects.equals(mainImageUrls, that.mainImageUrls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainImageUrls);
    }

    public static class AccommodationImageConverter extends JsonConverter<AccommodationImage> {
    }

}
