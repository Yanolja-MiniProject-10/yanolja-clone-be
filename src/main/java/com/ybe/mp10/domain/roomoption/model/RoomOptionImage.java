package com.ybe.mp10.domain.roomoption.model;

import com.ybe.mp10.domain.accommodation.model.AccommodationImage;
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
public class RoomOptionImage {
    private List<String> mainImageUrls;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomOptionImage that = (RoomOptionImage) o;
        return Objects.equals(mainImageUrls, that.mainImageUrls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainImageUrls);
    }

    public static class RoomOptionImageConverter extends JsonConverter<RoomOptionImage> {
    }
}
