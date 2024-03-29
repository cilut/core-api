package com.cilut.coreapi.dto;

public record CustomerOrderDto(int orderId,
                               int foodId,
                               String name,
                               String description,
                               Double price,
                               String restaurantId,
                               UserDto userDto){
}
