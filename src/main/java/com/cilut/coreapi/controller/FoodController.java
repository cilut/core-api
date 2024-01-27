package com.cilut.coreapi.controller;

import com.cilut.coreapi.dto.FoodMenuDto;
import com.cilut.coreapi.entity.FoodMenu;
import com.cilut.coreapi.service.FoodMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core-api/api/")
@CrossOrigin("*")
public class FoodController {

    private final FoodMenuService foodMenuService;

    @GetMapping("/foods")
    public List<FoodMenuDto> getAllFoods(){
        return this.foodMenuService.getAllFoods();
    }

    @GetMapping("/foods/restaurant/{id}")
    public List<FoodMenu> getAllFoodByRestaurantId(@PathVariable String id){
        return this.foodMenuService.getAllFoodsByRestaurantId(id);
    }
}
