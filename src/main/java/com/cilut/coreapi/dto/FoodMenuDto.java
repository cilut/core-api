package com.cilut.coreapi.dto;

import java.io.Serializable;

public record FoodMenuDto(int id,
                          String name,
                          String description,
                          double price,
                          String image,
                          RestaurantDto restaurant) implements Serializable {
}
