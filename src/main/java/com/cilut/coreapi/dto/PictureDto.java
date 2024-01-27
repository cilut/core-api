package com.cilut.coreapi.dto;

import com.cilut.coreapi.entity.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public record PictureDto(UUID id,
                      Date creationDate,
                      String extension,
                      boolean deleted
) implements Serializable {
}