package com.cilut.coreapi.dto;

import com.cilut.coreapi.entity.Address;
import com.cilut.coreapi.entity.FoodMenu;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public record RestaurantDto(UUID id,
                            String name,
                            String categories,
                            String contact,
                            String image,
                            Address addresses,
                            Set<FoodMenu> foodMenuList,
                            RestaurantAdminDto restaurantAdminDto) implements Serializable {
}
