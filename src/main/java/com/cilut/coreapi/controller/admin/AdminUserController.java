package com.cilut.coreapi.controller.admin;

import com.cilut.coreapi.dto.FoodMenuDto;
import com.cilut.coreapi.dto.RestaurantDto;
import com.cilut.coreapi.entity.FoodMenu;
import com.cilut.coreapi.request.FoodRequest;
import com.cilut.coreapi.service.CloudinaryImgService;
import com.cilut.coreapi.service.FoodMenuService;
import com.cilut.coreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/core-api/api/users/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminUserController {

    private final UserService userService;
    private final FoodMenuService foodMenuService;
    private final CloudinaryImgService cloudinaryImgService;

    @GetMapping("/{adminId}/restaurants")
    public RestaurantDto getAdminRestaurants(@PathVariable String adminId){
        return this.userService.getRestaurantByAdmin(adminId);
    }

    @PostMapping("/foods/food-details")
    public FoodMenuDto getFoodDetails(@RequestBody FoodRequest foodRequest){
        return this.foodMenuService.findById(foodRequest.foodId(), foodRequest.restaurantId());
    }

    @PutMapping("/foods/update/{id}")
    public ResponseEntity<?> updateFood(@PathVariable int id, @RequestBody FoodMenu newFood){
        FoodMenuDto updatedFood = this.foodMenuService.updateFood(id, newFood);
        return new ResponseEntity<>(updatedFood, HttpStatus.OK);
    }

    @PostMapping("/foods/add-food")
    public ResponseEntity<?> addFood(MultipartHttpServletRequest request) {

        MultipartFile file = request.getFile("image");
        String restaurantId = request.getParameter("restaurantId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Double price = Double.valueOf(request.getParameter("price"));

        FoodMenu food = new FoodMenu();
        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);
        if (file != null){
            String imageUrl = this.cloudinaryImgService.uploadImage(file);
            food.setImage(imageUrl);
        }

        FoodMenuDto foodMenuDto = this.foodMenuService.saveFood(food, restaurantId);
        return new ResponseEntity<>(foodMenuDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/foods/delete/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable int foodId){
        this.foodMenuService.deleteFood(foodId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
