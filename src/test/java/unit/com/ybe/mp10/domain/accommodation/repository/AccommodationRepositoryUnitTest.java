package com.ybe.mp10.domain.accommodation.repository;

import com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.ybe.mp10.domain.region.model.Region.SEOUL;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
class AccommodationRepositoryUnitTest {
    @Autowired
    AccommodationRepository accommodationRepository;

    @Test
    @DisplayName("기본 전체 상품 조회 성공 테스트")
    public void getAccommodationsByStartDateAndEndDateAndGuest() throws Exception{
        // given

        // when
        Page<AccommodationResponse> accommodationPages = accommodationRepository.getAccommodations(LocalDate.now(), LocalDate.of(2023, 12, 31), LocalDateTime.now(), LocalDateTime.of(2023,12, 31, 12, 0), 2L, Pageable.ofSize(20));

        // then
        assert accommodationPages != null;
        List<AccommodationResponse> content = accommodationPages.getContent();
        AccommodationResponse minimumPrice = content.stream().min(Comparator.comparingLong(AccommodationResponse::getMinimumPrice)).get();
        assert content.get(0).getMinimumPrice().equals(minimumPrice.getMinimumPrice());
    }

    @Test
    @DisplayName("숙소 이름으로 검색 테스트")
    public void searchAccommodationByName() throws Exception{
        // given


        // when
        Page<AccommodationResponse> searchPage = accommodationRepository.getAccommodationsByName(LocalDate.now(), LocalDate.of(2023, 12, 31), LocalDateTime.now(), LocalDateTime.of(2023, 12, 31, 12, 0), 2L, "모텔", Pageable.ofSize(10));
        // then
        assert searchPage != null;
        List<AccommodationResponse> content = searchPage.getContent();
        List<AccommodationResponse> filteredList = content.stream().filter(accommodationResponse ->
                accommodationResponse.getName().contains("모텔")
        ).toList();
        assert content.size() == filteredList.size();
    }

    @Test
    @DisplayName("지역으로 검색 테스트")
    public void searchAccommodationByRegion() throws Exception{
        // given


        // when
        Page<AccommodationResponse> searchPage = accommodationRepository.getAccommodationsByRegion(LocalDate.now(), LocalDate.of(2023, 12, 31), LocalDateTime.now(), LocalDateTime.of(2023, 12, 31, 12, 0), 2L, SEOUL, Pageable.ofSize(10));
        // then
        assert searchPage != null;
        List<AccommodationResponse> content = searchPage.getContent();
        List<AccommodationResponse> filteredList = content.stream().filter(accommodationResponse ->
                accommodationResponse.getRegion().equals(SEOUL.getValue())).toList();
        assert content.size() == filteredList.size();
    }
}