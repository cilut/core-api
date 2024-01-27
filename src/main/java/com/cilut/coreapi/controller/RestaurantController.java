package com.cilut.coreapi.controller;

import com.cilut.coreapi.entity.Address;
import com.cilut.coreapi.entity.Restaurant;
import com.cilut.coreapi.service.CloudinaryImgService;
import com.cilut.coreapi.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/core-api/dev")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CloudinaryImgService cloudinaryImgService;

    @GetMapping("/add-restaurant")
    public String restaurantPageView(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "save-restaurant";
    }

    @PostMapping("/save-restaurant")
    public String saveRestaurant(@ModelAttribute Restaurant restaurant, @ModelAttribute Address address, Model model,
                                 @RequestParam("file")MultipartFile file) {
        restaurant.setAddress(address);
        address.setRestaurant(restaurant);

        String imageUrl = cloudinaryImgService.uploadImage(file);
        restaurant.setImage(imageUrl);

        model.addAttribute("restaurant", restaurant);
        this.restaurantService.saveRestaurant(restaurant);

        return "redirect:add-restaurant";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "Hello World This is testing 4";
    }

}
