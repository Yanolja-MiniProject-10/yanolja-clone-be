package com.ybe.mp10.domain.user.controller;

import static com.ybe.mp10.global.common.constant.ResponseConstant.DELETED;
import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;
import static com.ybe.mp10.global.common.constant.ResponseConstant.UPDATED;


import com.ybe.mp10.domain.user.dto.request.UpdatedUserRequest;
import com.ybe.mp10.domain.user.dto.response.UserResponse;
import com.ybe.mp10.domain.user.service.UserService;
import com.ybe.mp10.global.response.GlobalDataResponse;
import com.ybe.mp10.global.response.GlobalResponse;
import com.ybe.mp10.global.security.token.UserPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 API", description = "사용자 관련 API 입니다.")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 정보")
    @ApiResponse(responseCode = "200", description = "조회될 시", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("")
    public GlobalDataResponse<UserResponse> getUser(@AuthenticationPrincipal UserPayload userPayload) {
        return GlobalDataResponse.ok(SUCCESS, userService.findByEmail(userPayload.getName()));
    }

    @Operation(summary = "사용자 정보 수정")
    @ApiResponse(responseCode = "200", description = "수정완료")
    @PutMapping("")
    public GlobalDataResponse<UserResponse> updateUser(@AuthenticationPrincipal UserPayload userPayload,
        @Valid @Parameter @RequestBody UpdatedUserRequest updatedUserRequest) {
        return GlobalDataResponse.ok(UPDATED,
            userService.updateUser(userPayload.getName(), updatedUserRequest));
    }

    @Operation(summary = "사용자 탈퇴")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping("")
    public GlobalResponse deleteUser(@AuthenticationPrincipal UserPayload userPayload) {
        userService.deleteUser(userPayload.getName());
        return GlobalResponse.ok(DELETED);
    }
}
