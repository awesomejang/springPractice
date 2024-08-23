package com.springpractice.api.auth;

import com.springpractice.common.JwtTokenProvider;
import com.springpractice.common.RedisService;
import com.springpractice.dtos.AuthTokenRequestDto;
import com.springpractice.dtos.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    public AuthController(JwtTokenProvider jwtTokenProvider, RedisService redisService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisService = redisService;
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto<Map<String, String>>> generateToken(@Validated @RequestBody AuthTokenRequestDto authTokenRequestDto) {
        // TODO: RestControllerAdvice -> 401 같은거 처리
        // TODO: clientId, clientSecret 검증 -> DB에 저장해놓고 뭐 상태 값 같은거 비교, 유효기간
        /**
         * // ID, PW는 무조건 통과
         *String username = loginRequest.getUsername();
         *
         *         // Access Token 생성
         * String accessToken = jwtTokenProvider.generateToken(username
         */
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
