package com.ybe.mp10.domain.accommodation.model;

import com.ybe.mp10.domain.accommodation.model.AccommodationImage.AccommodationImageConverter;
import com.ybe.mp10.domain.cart.model.CartProduct;
import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;
    private String name;
    @Convert(converter = AccommodationImageConverter.class)
    @Column(columnDefinition = "JSON")
    private AccommodationImage accommodationImage;
    private String description;
    private String thumbnailImageUrl;
    private String address;
    @Enumerated(STRING)
    private Region region;
    @Enumerated(STRING)
    private Category category;



    @OneToMany(mappedBy = "accommodation", fetch = LAZY)
    private final List<RoomOption> roomOptions = new ArrayList<>();

    public void updateThumbnail(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }
    public void updateImage(AccommodationImage accommodationImage) {
        this.accommodationImage = accommodationImage;
    }
}
