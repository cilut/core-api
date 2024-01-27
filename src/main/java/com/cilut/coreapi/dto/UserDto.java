package com.cilut.coreapi.dto;

import com.cilut.coreapi.entity.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public record UserDto(UUID id,
                      String name,
                      String email,
                      String phone,
                      String address,
                      boolean enable,
                      Role role,
                      Date birthdate,
                      String description,
                      String profilePhoto
                        ) implements Serializable {
}