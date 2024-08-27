package com.springpractice.api.auth;

import com.springpractice.api.auth.service.UserAuthService;
import com.springpractice.common.JwtTokenProvider;
import com.springpractice.common.RedisService;
import com.springpractice.dtos.AuthTokenRequestDto;
import com.springpractice.dtos.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/token")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final UserAuthService userAuthService;

    public AuthController(JwtTokenProvider jwtTokenProvider, RedisService redisService, UserAuthService userAuthService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisService = redisService;
        this.userAuthService = userAuthService;
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto<Map<String, String>>> generateToken(@Validated @RequestBody AuthTokenRequestDto authTokenRequestDto) {
        boolean isValidClient = userAuthService.checkValidUser(authTokenRequestDto.clientId(), authTokenRequestDto.clientSecret());
        if (!isValidClient) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponseDto.fail("Invalid client id or client secret"));
        }

        String accessToken = jwtTokenProvider.generateToken(authTokenRequestDto);
        String refreshToken = jwtTokenProvider.generateToken(authTokenRequestDto);
        redisService.saveRefreshToken(authTokenRequestDto.clientId(), refreshToken);

        return ResponseEntity.ok(CommonResponseDto.success(Map.of("accessToken", accessToken, "refreshToken", refreshToken)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto<Map<String, String>>> refreshToken(@RequestBody Map<String, String> payload) {
        if (payload.get("refreshToken") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponseDto.fail("Refresh token is required"));
        }

        return ResponseEntity.ok(CommonResponseDto.success(jwtTokenProvider.refreshToken(payload.get("refreshToken"))));
    }
}
