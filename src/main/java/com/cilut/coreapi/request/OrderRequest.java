package com.cilut.coreapi.request;

import com.cilut.coreapi.dto.UserDto;

public record OrderRequest (int id,
                            String name,
                            String description,
                            Double price,
                            String restaurantId,
                            UserDto userDto){
}
