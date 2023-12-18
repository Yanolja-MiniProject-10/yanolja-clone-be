package com.ybe.mp10.domain.roomoption.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.cart.model.CartProduct;
import com.ybe.mp10.domain.roomoption.model.RoomOptionImage.RoomOptionImageConverter;
import com.ybe.mp10.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = JOINED)
public class RoomOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "room_option_id")
    private Long id;
    private Long defaultPrice;
    @Convert(converter = RoomOptionImageConverter.class)
    @Column(columnDefinition = "JSON")
    @Setter
    private RoomOptionImage roomOptionImage;
    private String description;
    @Setter
    private String thumbnailImage;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Long totalRoomCount; // 총 룸 옵션 방 개수
    private String name;
    private Long capacity; // 방 별
    @Builder.Default
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "roomOption", fetch = LAZY)
    private List<CartProduct> cartProducts = new ArrayList<>();
    @OneToMany(mappedBy = "roomOption", fetch = LAZY)
    private List<Price> prices = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


}
