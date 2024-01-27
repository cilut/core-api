package com.cilut.coreapi.service;

import com.cilut.coreapi.response.RestaurantResponse;
import com.cilut.coreapi.dto.RestaurantDto;
import com.cilut.coreapi.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    void saveRestaurant(Restaurant restaurant);
    RestaurantDto findRestaurantById(String id);
    RestaurantResponse getAllRestaurant(int pageNumber, int pageSize);
    List<RestaurantDto> findNearRestaurant(double latitude, double longitude, double radius);
}
