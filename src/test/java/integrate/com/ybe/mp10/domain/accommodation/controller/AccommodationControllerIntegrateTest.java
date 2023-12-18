package com.ybe.mp10.domain.accommodation.controller;

import com.ybe.mp10.global.common.annotation.WithMockUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AccommodationControllerIntegrateTest {
    @Autowired
    MockMvc mvc;
    private static final String BASE_URL = "/accommodations";
    @Test
    @DisplayName("숙박 상품 조회 성공 테스트")
    public void getAccommodationsTest() throws Exception {
        // given
        String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("startDate", "2023-11-27")
                .queryParam("endDate", "2023-11-28")
                .queryParam("guest", 2)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .build().toString();

        // when
        ResultActions resultActions = mvc.perform(get(uri)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));

    }
}