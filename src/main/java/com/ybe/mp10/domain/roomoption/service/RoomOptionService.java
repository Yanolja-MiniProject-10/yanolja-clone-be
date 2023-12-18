package com.ybe.mp10.domain.roomoption.service;

import com.ybe.mp10.domain.roomoption.dto.request.PriceRequest;
import com.ybe.mp10.domain.roomoption.dto.request.RoomOptionCreateRequest;
import com.ybe.mp10.domain.roomoption.model.Price;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.domain.roomoption.model.RoomOptionImage;
import com.ybe.mp10.domain.roomoption.repository.PriceRepository;
import com.ybe.mp10.domain.roomoption.repository.RoomOptionRepository;
import com.ybe.mp10.global.common.enums.ImageType;
import com.ybe.mp10.global.component.S3ImageComponent;
import com.ybe.mp10.global.response.GlobalDataResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomOptionService {

    private final RoomOptionRepository roomOptionRepository;
    private final PriceRepository priceRepository;
    private final S3ImageComponent s3ImageComponent;

    public RoomOption createRoomOption(
        RoomOptionCreateRequest roomOptionCreateRequest,
        MultipartFile thumbNailFile,
        List<MultipartFile> roomOptionImageFiles) {

        RoomOption roomOption = roomOptionRepository.save(
            RoomOptionCreateRequest.toEntity(roomOptionCreateRequest));

        String thumbNail = s3ImageComponent.uploadImage(
            ImageType.ROOM_OPTION_THUMBNAIL.getValue()
            , roomOption.getId()
            , thumbNailFile
        );

        List<String> roomOptionImages = s3ImageComponent.uploadImages(
            ImageType.ROOM_OPTION_MAIN.getValue(),
            roomOption.getId(),
            roomOptionImageFiles
        );

        RoomOptionImage roomOptionImage = new RoomOptionImage(roomOptionImages);
        roomOption.setThumbnailImage(thumbNail);
        roomOption.setRoomOptionImage(roomOptionImage);

        return roomOption;
    }

    public Price modifiedRoomOptionPrice(PriceRequest priceRequest) {

        return priceRepository.save(PriceRequest.toEntity(priceRequest));
    }
}
