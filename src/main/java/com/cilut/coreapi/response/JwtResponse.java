package com.cilut.coreapi.response;

import com.cilut.coreapi.dto.UserDto;

public record JwtResponse (String token, UserDto userDto) {
}
