package com.ybe.mp10.global.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.domain.festival.dto.response.FestivalMainImageResponse;
import com.ybe.mp10.domain.open_api.dto.request.UpdateFestivalsRequest;
import com.ybe.mp10.domain.open_api.dto.response.AreaCodeResponse.AreaCode;
import com.ybe.mp10.domain.open_api.dto.response.AreaOpenApiResponse;
import com.ybe.mp10.domain.open_api.dto.response.FestivalOpenApiResponse;
import com.ybe.mp10.domain.open_api.dto.response.FestivalOpenApiResponse.FestivalResponse;
import com.ybe.mp10.domain.open_api.dto.response.SigunguCodeResponse.SigunguCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenApiComponent {
    @Value("${open-api.url}")
    private String apiUrl;
    @Value("${open-api.key}")
    private String apiKey;
    private static final String AREA = "/areaCode1";
    private static final String FESTIVAL = "/searchFestival1";
    private final RestTemplate restTemplate;

    public List<AreaCode> getAreaCode() {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(apiUrl+AREA)
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", "100")
                .queryParam("pageNo", "1")
                .queryParam("_type", "json")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .build();
        log.info(uri.toUriString());
        ResponseEntity<AreaOpenApiResponse> apiResponseEntity = restTemplate.getForEntity(uri.toUriString(), AreaOpenApiResponse.class);
        AreaOpenApiResponse apiResponse = apiResponseEntity.getBody();
        return apiResponse.getResponse().getBody().getItems().getItem().stream().map(m -> {
            return new AreaCode(m.getCode(), m.getName());
        }).toList();
    }

    public List<SigunguCode> getSigunguCode(String code) {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(apiUrl + AREA)
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", "100")
                .queryParam("pageNo", "1")
                .queryParam("_type", "json")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("areaCode", code)
                .build();
        log.info(uri.toUriString());
        ResponseEntity<AreaOpenApiResponse> apiResponseEntity = restTemplate.getForEntity(uri.toUriString(), AreaOpenApiResponse.class);
        AreaOpenApiResponse apiResponse = apiResponseEntity.getBody();
        return apiResponse.getResponse().getBody().getItems().getItem().stream().map(m -> {
            return new SigunguCode(m.getCode(), m.getName());
        }).toList();

    }

    public List<FestivalResponse> getFestivals(UpdateFestivalsRequest updateFestivalsRequest) {

        UriComponents uri = UriComponentsBuilder
                .fromUriString(apiUrl + FESTIVAL)
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", updateFestivalsRequest.getRow())
                .queryParam("pageNo", "1")
                .queryParam("_type", "json")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("areaCode", updateFestivalsRequest.getAreaCode())
                .queryParam("sigunguCode", updateFestivalsRequest.getSigunguCode())
                .queryParam("eventStartDate", updateFestivalsRequest.getEventStartDate())
                .build();
        log.info(uri.toUriString());
        ResponseEntity<FestivalOpenApiResponse> festivalOpenApiResponseResponseEntity = restTemplate.getForEntity(uri.toUriString(), FestivalOpenApiResponse.class);
        FestivalOpenApiResponse festivalOpenApiResponse = festivalOpenApiResponseResponseEntity.getBody();
        return festivalOpenApiResponse.getResponse().getBody().getItems().getItem();


    }
}
