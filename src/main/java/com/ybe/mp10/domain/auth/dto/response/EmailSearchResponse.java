package com.ybe.mp10.domain.auth.dto.response;

import com.ybe.mp10.global.response.GlobalDataResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSearchResponse {

    @Schema(description = "true면 이미 이메일이 존재한다는 의미.")
    private boolean exists;

    public EmailSearchResponse(Boolean exists) {
        this.exists = exists;
    }
}
