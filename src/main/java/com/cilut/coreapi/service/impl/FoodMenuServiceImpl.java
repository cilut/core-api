package com.cilut.coreapi.service.impl;

import com.cilut.coreapi.dto.FoodMenuDto;
import com.cilut.coreapi.entity.FoodMenu;
import com.cilut.coreapi.entity.Restaurant;
import com.cilut.coreapi.exceptions.ResourceNotFoundException;
import com.cilut.coreapi.repository.FoodMenuRepository;
import com.cilut.coreapi.repository.RestaurantRepository;
import com.cilut.coreapi.service.FoodMenuService;
import com.cilut.coreapi.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    @CacheEvict(value = {"restaurantFoods", "restaurantByAdmin"}, allEntries = true)
    public FoodMenuDto saveFood(FoodMenu foodMenu, String restaurantId) {
        Restaurant restaurant = this.restaurantRepository.findById(UUID.fromString(restaurantId))
                .orElseThrow(() -> new ResourceNotFoundException("Restaurants NOT FOUND! with this id: " + restaurantId));

        foodMenu.setRestaurant(restaurant);
        FoodMenu savedFood = this.foodMenuRepository.save(foodMenu);
        return ModelMapper.foodMenuToDto(savedFood, restaurant);
    }

    @Override
    public FoodMenuDto findById(int id, String restaurantId) {
        FoodMenu foodMenu = this.foodMenuRepository.findByIdAndRestaurantId(id, UUID.fromString(restaurantId))
                .orElseThrow(() -> new ResourceNotFoundException("Food Not FOUND with this id : " + id + ", and restaurant : " + restaurantId));

        return ModelMapper.foodMenuToDto(foodMenu, foodMenu.getRestaurant());
    }

    @Override
    public List<FoodMenuDto> getAllFoods() {
        List<FoodMenu> allFoods = this.foodMenuRepository.findAll();

        List<FoodMenuDto> foodMenuDtoList = new ArrayList<>();

        for (FoodMenu foodMenu : allFoods){
            Restaurant restaurant = foodMenu.getRestaurant();

            FoodMenuDto foodMenuDto = ModelMapper.foodMenuToDto(foodMenu, restaurant);
            foodMenuDtoList.add(foodMenuDto);
        }
        return foodMenuDtoList;
    }

    @Override
    //@Cacheable(key = "#id", value = "restaurantFoods")
    public List<FoodMenu> getAllFoodsByRestaurantId(String id) {
        this.restaurantRepository.findById(UUID.fromString(id)).orElseThrow(()->
                new ResourceNotFoundException("Restaurant Not FOUND with this id: " + id));

        return this.foodMenuRepository.findFoodMenuByRestaurantId(UUID.fromString(id));
    }

    @Override
    //@CacheEvict(value = {"restaurantFoods", "restaurantByAdmin"}, allEntries = true)
    public FoodMenuDto updateFood(int id, FoodMenu newFood) {
        FoodMenu oldFood = this.foodMenuRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Food Not FOUND with this id : " + id));

        newFood.setId(oldFood.getId());
        newFood.setRestaurant(oldFood.getRestaurant());
        FoodMenu updatedFood = this.foodMenuRepository.save(newFood);
        return ModelMapper.foodMenuToDto(updatedFood, updatedFood.getRestaurant());
    }


    @Override
    @CacheEvict(value = {"restaurantFoods", "restaurantByAdmin"}, allEntries = true)
    public void deleteFood(int foodId) {
        FoodMenu food = this.foodMenuRepository.findById(foodId).orElseThrow(() ->
                new ResourceNotFoundException("Food Not FOUND with this id : " + foodId));
        this.foodMenuRepository.delete(food);
    }
}
