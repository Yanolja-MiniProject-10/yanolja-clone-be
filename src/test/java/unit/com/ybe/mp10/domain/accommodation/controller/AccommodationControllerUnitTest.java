package com.ybe.mp10.domain.accommodation.controller;

import com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse;
import com.ybe.mp10.domain.accommodation.exception.AccommodationException;
import com.ybe.mp10.domain.accommodation.repository.AccommodationRepository;
import com.ybe.mp10.domain.accommodation.service.AccommodationService;
import com.ybe.mp10.domain.roomoption.dto.response.RoomOptionResponse;
import com.ybe.mp10.global.common.annotation.WithMockUser;
import com.ybe.mp10.global.common.constant.ResponseConstant;
import com.ybe.mp10.global.response.GlobalDataResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.ybe.mp10.domain.category.model.Category.HOTEL_RESORT;
import static com.ybe.mp10.domain.region.model.Region.SEOUL;
import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccommodationController.class)
@ActiveProfiles("test")
class AccommodationControllerUnitTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    AccommodationController accommodationController;
    @MockBean
    AccommodationService mockAccommodationService;
    @MockBean
    AccommodationRepository mockAccommodationRepository;

    @Autowired
    AccommodationRepository accommodationRepository;
    private static final String BASE_URL = "/accommodations";
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
    @WithMockUser
    @DisplayName("숙박 상품 조회 성공 테스트")
    public void getAccommodationsTest() throws Exception {
        // given
        PageImpl<AccommodationResponse> pageResponse = new PageImpl<>(accommodationResponseList, Pageable.ofSize(10), 20L);

        ResponseEntity<GlobalDataResponse<Page<AccommodationResponse>>> response = ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, pageResponse));
        String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("startDate", "2023-11-28")
                .queryParam("endDate", "2023-11-29")
                .queryParam("guest", 2)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .build()
                .toString();
        // when
        when(mockAccommodationService.getAccommodations(any(), any(), any(), any(), any(), any())).thenReturn(pageResponse);
        ResultActions resultActions = mvc.perform(get(uri)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print());


        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @Test
    @WithMockUser
    @DisplayName("숙박 상품 조회 실패 테스트 - 유효하지 않은 숙박 기간")
    public void getAccommodationsFailByInvalidStayDuration() throws Exception{
        // given
        String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("startDate", "2023-11-28")
                .queryParam("endDate", "2023-11-27")
                .queryParam("guest", 2)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .build()
                .toString();

        // when
        when(mockAccommodationService.getAccommodations(any(), any(), any(), any(), any(), any())).thenThrow(AccommodationException.class);
        ResultActions resultActions = mvc.perform(get(uri)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isConflict());
    }


    @Test
    @WithMockUser
    @DisplayName("숙박 상품 조회 실패 테스트 - 유효하지 않은 게스트")
    public void getAccommodationsFailByGuest() throws Exception{
        // given
        String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("startDate", "2023-11-28")
                .queryParam("endDate", "2023-11-29")
                .queryParam("guest", 0)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .build()
                .toString();

        // when
        when(mockAccommodationService.getAccommodations(any(), any(), any(), any(), any(), any())).thenThrow(AccommodationException.class);
        ResultActions resultActions = mvc.perform(get(uri)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isConflict());
    }


}