package com.springpractice.dtos;

import jakarta.validation.constraints.NotEmpty;

public record AuthTokenRequestDto(
        @NotEmpty
        String clientId,
        @NotEmpty
        String clientSecret
) {

}
