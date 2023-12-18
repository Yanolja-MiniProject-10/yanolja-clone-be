package com.ybe.mp10.domain.festival.service;

import com.ybe.mp10.domain.festival.dto.response.FestivalThumbnailResponse;
import com.ybe.mp10.domain.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FestivalService {
    private final FestivalRepository festivalRepository;
    @Transactional(readOnly = true)
    public Page<FestivalThumbnailResponse> getFestival(LocalDate eventStartDate, Pageable pageable) {
        return festivalRepository.findFestivalResponseByEventStartDate(eventStartDate, pageable);
    }
}
