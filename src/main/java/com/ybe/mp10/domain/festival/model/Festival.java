package com.ybe.mp10.domain.festival.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Festival {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "festival_id")
    private Long id;
    private String title;
    private String phoneNumber;
    private String mainImageUrl;
    private String thumbnailImageUrl;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private String address;
    private String area;
    private String city;
    private String areaCode;
    private String sigunguCode;
}
