package com.ybe.mp10.domain.open_api.service;

import com.ybe.mp10.domain.festival.model.Festival;
import com.ybe.mp10.domain.festival.repository.FestivalRepository;
import com.ybe.mp10.domain.open_api.dto.request.UpdateFestivalsRequest;
import com.ybe.mp10.domain.open_api.dto.response.AreaCodeResponse;
import com.ybe.mp10.domain.open_api.dto.response.FestivalOpenApiResponse;
import com.ybe.mp10.domain.open_api.dto.response.FestivalOpenApiResponse.FestivalResponse;
import com.ybe.mp10.domain.open_api.dto.response.SigunguCodeResponse;
import com.ybe.mp10.domain.open_api.dto.response.UpdateFestivalsResponse;
import com.ybe.mp10.global.component.OpenApiComponent;
import com.ybe.mp10.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ybe.mp10.global.util.StringUtil.fromPlainStringToLocalDate;

@Service
@RequiredArgsConstructor
public class OpenApiService {
    private final OpenApiComponent openApiComponent;
    private final FestivalRepository festivalRepository;
    @Transactional(readOnly = true)
    public AreaCodeResponse getAreaCode() {
        return AreaCodeResponse.builder()
                .areaCodes(openApiComponent.getAreaCode())
                .build();
    }

    @Transactional(readOnly = true)
    public SigunguCodeResponse getSigunguCode(String areaCode) {
        return SigunguCodeResponse.builder()
                .sigunguCodes(openApiComponent.getSigunguCode(areaCode))
                .build();
    }

    @Transactional
    public UpdateFestivalsResponse updateFestivalInfo(UpdateFestivalsRequest updateFestivalsRequest) {
        festivalRepository.deleteFestivalBySigunguCode(updateFestivalsRequest.getSigunguCode());
        List<FestivalResponse> festivals = openApiComponent.getFestivals(updateFestivalsRequest);
        List<Festival> saved = festivalRepository.saveAll(
                festivals.stream().map(f -> {
                    return Festival.builder()
                            .title(f.getTitle())
                            .phoneNumber(f.getTel())
                            .mainImageUrl(f.getFirstimage())
                            .thumbnailImageUrl(f.getFirstimage2())
                            .eventStartDate(fromPlainStringToLocalDate(f.getEventstartdate()))
                            .eventEndDate(fromPlainStringToLocalDate(f.getEventenddate()))
                            .areaCode(f.getAreacode())
                            .sigunguCode(f.getSigungucode())
                            .area(updateFestivalsRequest.getArea())
                            .city(updateFestivalsRequest.getSigungu())
                            .address(f.getAddr1())
                            .build();
                }).toList()
        );
        return UpdateFestivalsResponse.builder()
                .updatedFestivals(saved)
                .totalUpdated((long) saved.size())
                .build();
    }

}
