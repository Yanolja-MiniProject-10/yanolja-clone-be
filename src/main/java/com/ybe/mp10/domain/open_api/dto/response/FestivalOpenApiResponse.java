package com.ybe.mp10.domain.open_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FestivalOpenApiResponse {
    Response response;
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        Header header;
        Body body;


    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        String resultCode;
        String resultMsg;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body {
        Item items;
        Integer numOfRows;
        Integer pageNo;
        Integer totalCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        List<FestivalResponse> item;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FestivalResponse {
        String addr1;
        String addr2;
        String bookTour;
        String cat1;
        String cat2;
        String cat3;
        String contentId;
        String contenttypeid;
        String createdtime;
        String eventstartdate;
        String eventenddate;
        String firstimage;
        String firstimage2;
        String cpyrhtDivCd;
        String mapx;
        String mapy;
        String mlevel;
        String modifiedtime;
        String areacode;
        String sigungucode;
        String tel;
        String title;
    }
}
