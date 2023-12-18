package com.ybe.mp10.domain.accommodation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.ybe.mp10.domain.accommodation.dto.request.RoomOptionRequest;
import com.ybe.mp10.domain.accommodation.dto.response.*;
import com.ybe.mp10.domain.accommodation.exception.AccommodationException;
import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.accommodation.model.AccommodationImage;
import com.ybe.mp10.domain.accommodation.repository.AccommodationRepository;
import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import com.ybe.mp10.domain.roomoption.dto.response.RoomOptionResponse;
import com.ybe.mp10.domain.roomoption.exception.RoomOptionException;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.domain.roomoption.model.RoomOptionImage;
import com.ybe.mp10.domain.roomoption.repository.RoomOptionRepository;
import com.ybe.mp10.global.component.S3ImageComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.ybe.mp10.domain.region.model.Region.fromValue;
import static com.ybe.mp10.global.common.constant.ResponseConstant.CREATED;
import static com.ybe.mp10.global.common.constant.ResponseConstant.UPDATED;
import static com.ybe.mp10.global.common.enums.ImageType.*;
import static com.ybe.mp10.global.util.TimeUtil.toDateTime;
import static com.ybe.mp10.global.validator.AccommodationValidator.validateStartDateAndEndDateAndGuest;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final RoomOptionRepository roomOptionRepository;
    private final S3ImageComponent s3ImageComponent;
    private final ObjectMapper mapper;

    @Transactional
    public AccommodationCreateResponse createAccommodation(AccommodationCreateRequest accommodationCreateRequest) {
        Accommodation accommodation = AccommodationCreateRequest.toEntity(accommodationCreateRequest);

        accommodationRepository.save(accommodation);

        return AccommodationCreateResponse.fromEntity(accommodation);
    }


    @Transactional
    public AccommodationThumbnailUpdateResponse updateThumbnailImage(Long accommodationId, MultipartFile mainThumbnail) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new AccommodationException("주어진 아이디로 존재하는 숙박 상품을 찾을 수 없습니다.", NOT_FOUND));
        String thumbnailUrl = s3ImageComponent.uploadImage(ACCOMMODATION_THUMB_NAIL.getValue(), accommodation.getId(), mainThumbnail);
        accommodation.updateThumbnail(thumbnailUrl);

        return AccommodationThumbnailUpdateResponse.builder().thumbnailUrl(thumbnailUrl).build();
    }

    @Transactional
    public AccommodationMainImageUpdateResponse updateMainImages(Long accommodationId, List<MultipartFile> mainImages) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new AccommodationException("주어진 아이디로 존재하는 숙박 상품을 찾을 수 없습니다.", NOT_FOUND));
        List<String> mainImageUrls = s3ImageComponent.uploadImages(ACCOMMODATION_MAIN.getValue(), accommodation.getId(), mainImages);
        AccommodationImage accommodationImage = new AccommodationImage(mainImageUrls);
        accommodation.updateImage(accommodationImage);
        return AccommodationMainImageUpdateResponse.builder().mainImageUrls(mainImageUrls).build();
    }

    @Transactional
    public void deleteAccommodation(Long accommodationId) {
        accommodationRepository.deleteById(accommodationId);

    }

    @Transactional(readOnly = true)
    public AccommodationRoomOptionResponse findById(Long accommodationId, LocalDate startDate, LocalDate endDate, Long guest) {
        validateStartDateAndEndDateAndGuest(startDate, endDate, guest);
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new AccommodationException("주어진 아이디로 존재하는 숙박 상품을 찾을 수 없습니다.", NOT_FOUND));
        List<RoomOptionResponse> roomOptionResponses = roomOptionRepository.findRoomOptionResponseByAccommodationIdNativeQuery(accommodationId, toDateTime(startDate), toDateTime(endDate), startDate, endDate).stream().map(data -> new RoomOptionResponse(data, mapper)).toList();

        return AccommodationRoomOptionResponse.fromEntity(accommodation, roomOptionResponses);
    }

    @Transactional(readOnly = true)
    public Page<AccommodationResponse> getAccommodations(LocalDate startDate, LocalDate endDate, Long guest, String name, String region, Pageable pageable) {
        validateStartDateAndEndDateAndGuest(startDate, endDate, guest);
        if (name != null && region != null) {
            return accommodationRepository.getAccommodationsByNameAndRegion(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, name, fromValue(region), pageable);
        }
        if (name != null) {
            return accommodationRepository.getAccommodationsByName(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, name, pageable);
        }
        if (region != null) {
            return accommodationRepository.getAccommodationsByRegion(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, fromValue(region), pageable);
        }
        return accommodationRepository.getAccommodations(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, pageable);

    }

    @Transactional(readOnly = true)
    public Page<AccommodationResponse> getAccommodationsByCategory(LocalDate startDate, LocalDate endDate, Long guest, String categoryString, Pageable pageable) {
        validateStartDateAndEndDateAndGuest(startDate, endDate, guest);
        Category category = Category.fromValue(categoryString);
        return accommodationRepository.getAccommodationsByCategory(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, category, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AccommodationResponse> getRelatedAccommodations(LocalDate startDate, LocalDate endDate, Long guest, String categoryString, String regionString, Pageable pageable) {
        validateStartDateAndEndDateAndGuest(startDate, endDate, guest);
        Category category = Category.fromValue(categoryString);
        Region region = fromValue(regionString);
        return accommodationRepository.getRelatedAccommodations(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, region, category, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AccommodationResponse> getRecommendedRegionAccommodation(LocalDate startDate, LocalDate endDate, Long guest, String regionString, Pageable pageable) {
        validateStartDateAndEndDateAndGuest(startDate, endDate, guest);
        Region region = fromValue(regionString);
        return accommodationRepository.getRecommendedRegionAccommodation(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, region, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AccommodationResponse> getRankingAccommodations(LocalDate startDate, LocalDate endDate, Long guest, Pageable pageable) {
        validateStartDateAndEndDateAndGuest(startDate, endDate, guest);
        return accommodationRepository.getRankingAccommodations(startDate, endDate, toDateTime(startDate), toDateTime(endDate), guest, pageable);
    }

    @Transactional(readOnly = true)
    public RoomOptionResponse findRoomOptionById(Long roomOptionId, LocalDate startDate, LocalDate endDate, Long guest) {
        validateStartDateAndEndDateAndGuest(startDate, endDate, guest);
        return roomOptionRepository.findRoomOptionByRoomOptionId(roomOptionId, toDateTime(startDate), toDateTime(endDate)).orElseThrow(() -> new AccommodationException("해당 아이디로 존재하는 룸 옵션이 없습니다", NOT_FOUND));
    }

    @Transactional
    public String addRoomOptions(RoomOptionRequest roomOptionRequest) {
        RoomOption roomOption = RoomOptionRequest.toEntity(roomOptionRequest);
        roomOptionRepository.save(roomOption);
        return CREATED;
    }

    @Transactional
    public String updateRoomOptionThumbnailImage(Long roomOptionId, MultipartFile mainThumbnail) {
        RoomOption roomOption = roomOptionRepository.findById(roomOptionId).orElseThrow(() -> new RoomOptionException("아이디로 존재하는 룸 옵션이 없습니다.", NOT_FOUND));
        String thumbnailUrl = s3ImageComponent.uploadImage(ROOM_OPTION_THUMBNAIL.getValue(), roomOptionId, mainThumbnail);
        roomOption.setThumbnailImage(thumbnailUrl);
        return UPDATED;
    }

    @Transactional
    public String updateRoomOptionMainImages(Long roomOptionId, List<MultipartFile> mainImages) {
        RoomOption roomOption = roomOptionRepository.findById(roomOptionId).orElseThrow(() -> new RoomOptionException("아이디로 존재하는 룸 옵션이 없습니다.", NOT_FOUND));
        List<String> mainImageUrls = s3ImageComponent.uploadImages(ROOM_OPTION_MAIN.getValue(), roomOptionId, mainImages);
        RoomOptionImage roomOptionImage = new RoomOptionImage(mainImageUrls);
        roomOption.setRoomOptionImage(roomOptionImage);
        return UPDATED;
    }
}
