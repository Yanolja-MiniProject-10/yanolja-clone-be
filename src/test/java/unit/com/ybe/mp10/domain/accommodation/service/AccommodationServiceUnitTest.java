package com.ybe.mp10.domain.accommodation.service;

import com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse;
import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.accommodation.repository.AccommodationRepository;
import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import com.ybe.mp10.domain.roomoption.dto.response.RoomOptionResponse;
import com.ybe.mp10.domain.roomoption.model.Price;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.domain.roomoption.repository.PriceRepository;
import com.ybe.mp10.domain.roomoption.repository.RoomOptionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.ybe.mp10.domain.category.model.Category.HOTEL_RESORT;
import static com.ybe.mp10.domain.region.model.Region.SEOUL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceUnitTest {

    @InjectMocks
    AccommodationService accommodationService;
    @Mock
    AccommodationRepository accommodationRepository;
    @Mock
    RoomOptionRepository roomOptionRepository;
    @Mock
    PriceRepository priceRepository;
    private static List<AccommodationResponse> accommodationResponseList;
    private static List<RoomOptionResponse> roomOptionResponseList;

    @BeforeAll
    public static void setReturnData() {
        accommodationResponseList = List.of(
                AccommodationResponse.builder().id(1L).name("1번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(2L).name("2번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(3L).name("3번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(4L).name("4번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(5L).name("5번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(6L).name("6번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(7L).name("7번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(8L).name("8번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(9L).name("9번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build(),
                AccommodationResponse.builder().id(10L).name("10번 숙소").category(HOTEL_RESORT).region(SEOUL).minimumPrice(50_000L).maximumPrice(150_000L).thumbnailImageUrl("썸네일").build()
        );

        roomOptionResponseList = List.of(
                RoomOptionResponse.builder().id(1L).accommodationId(1L).capacity(2L).dailyPrice(50_000L).name("1번 방").description("방 설명")
                        .checkInTime(LocalTime.of(15, 0)).checkOutTime(LocalTime.of(11, 0)).totalRoomCount(10L).reservedRoomCount(5L)
                        .startDate(LocalDateTime.of(2023, 11, 28, 0, 0)).endDate(LocalDateTime.of(2023, 11, 30, 0, 0)).build(),
                RoomOptionResponse.builder().id(2L).accommodationId(1L).capacity(2L).dailyPrice(50_000L).name("2번 방").description("방 설명")
                        .checkInTime(LocalTime.of(15, 0)).checkOutTime(LocalTime.of(11, 0)).totalRoomCount(10L).reservedRoomCount(6L)
                        .startDate(LocalDateTime.of(2023, 11, 28, 0, 0)).endDate(LocalDateTime.of(2023, 11, 30, 0, 0)).build(),
                RoomOptionResponse.builder().id(3L).accommodationId(1L).capacity(2L).dailyPrice(50_000L).name("3번 방").description("방 설명")
                        .checkInTime(LocalTime.of(15, 0)).checkOutTime(LocalTime.of(11, 0)).totalRoomCount(10L).reservedRoomCount(6L)
                        .startDate(LocalDateTime.of(2023, 11, 28, 0, 0)).endDate(LocalDateTime.of(2023, 11, 30, 0, 0)).build(),
                RoomOptionResponse.builder().id(4L).accommodationId(1L).capacity(4L).dailyPrice(50_000L).name("4번 방").description("방 설명")
                        .checkInTime(LocalTime.of(15, 0)).checkOutTime(LocalTime.of(11, 0)).totalRoomCount(10L).reservedRoomCount(6L)
                        .startDate(LocalDateTime.of(2023, 11, 28, 0, 0)).endDate(LocalDateTime.of(2023, 11, 30, 0, 0)).build(),
                RoomOptionResponse.builder().id(5L).accommodationId(1L).capacity(4L).dailyPrice(50_000L).name("5번 방").description("방 설명")
                        .checkInTime(LocalTime.of(15, 0)).checkOutTime(LocalTime.of(11, 0)).totalRoomCount(10L).reservedRoomCount(6L)
                        .startDate(LocalDateTime.of(2023, 11, 28, 0, 0)).endDate(LocalDateTime.of(2023, 11, 30, 0, 0)).build()
                );


    }


    @Test
    @DisplayName("전체 숙박 상품 조회 테스트")
    public void getAccommodationTest() throws Exception {
        // given
        PageImpl<AccommodationResponse> pageResponse = new PageImpl<>(accommodationResponseList, Pageable.ofSize(10), 20L);

        // when
        when(accommodationRepository.getAccommodations(any(), any(), any(), any(), any(), any())).thenReturn(pageResponse);
        // then
        Page<AccommodationResponse> accommodations = accommodationService.getAccommodations(LocalDate.now(), LocalDateTime.MAX.toLocalDate(), 2L, null, null, Pageable.unpaged());

        assert accommodations != null;
        assert pageResponse.getContent().size() == 10;
    }

    @Test
    @DisplayName("이름으로 숙박 상품 검색 테스트")
    public void getAccommodationsByNameTest() throws Exception{
        // given
        PageImpl<AccommodationResponse> pageResponse = new PageImpl<>(accommodationResponseList, Pageable.ofSize(10), 20L);


        // when
        when(accommodationRepository.getAccommodationsByName(any(), any(), any(), any(), any(), eq("숙소"), any())).thenReturn(pageResponse);

        // then
        Page<AccommodationResponse> accommodations = accommodationService.getAccommodations(LocalDate.now(), LocalDateTime.MAX.toLocalDate(), 2L, "숙소", null, Pageable.ofSize(10));

        assert accommodations != null;
        assert pageResponse.getContent().size() == 10;
    }

    @Test
    @DisplayName("지역으로 숙박 상품 검색 테스트")
    public void getAccommodationsByRegionTest() throws Exception{
        // given
        PageImpl<AccommodationResponse> pageResponse = new PageImpl<>(accommodationResponseList, Pageable.ofSize(10), 20L);


        // when
        when(accommodationRepository.getAccommodationsByRegion(any(), any(), any(), any(), any(), eq(SEOUL), any())).thenReturn(pageResponse);

        // then
        Page<AccommodationResponse> accommodations = accommodationService.getAccommodations(LocalDate.now(), LocalDateTime.MAX.toLocalDate(), 2L, null, SEOUL.getValue(), Pageable.ofSize(10));

        assert accommodations != null;
        assert pageResponse.getContent().size() == 10;
    }

    @Test
    @DisplayName("이름과 지역으로 숙박 상품 검색 테스트")
    public void getAccommodationsByNameAndRegion() throws Exception{
        // given
        PageImpl<AccommodationResponse> pageResponse = new PageImpl<>(accommodationResponseList, Pageable.ofSize(10), 20L);

        // when
        when(accommodationRepository.getAccommodationsByNameAndRegion(any(), any(), any(), any(), any(), eq("숙소"), eq(SEOUL), any())).thenReturn(pageResponse);
        // then
        Page<AccommodationResponse> accommodations = accommodationService.getAccommodations(LocalDate.now(), LocalDateTime.MAX.toLocalDate(), 2L, "숙소", SEOUL.getValue(), Pageable.ofSize(10));

        assert accommodations != null;
        assert pageResponse.getContent().size() == 10;
    }
}