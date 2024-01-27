package com.cilut.coreapi.service;

import com.cilut.coreapi.dto.RestaurantDto;
import com.cilut.coreapi.dto.UserDto;
import com.cilut.coreapi.entity.User;

import java.util.List;

public interface UserService {
    // create
    User saveUser(User user);

    // get
    UserDto getUser(String id);

    // verify user
    boolean isUserVerified(String verificationCode);

    // update
    UserDto updateUser(String id, User user);

    // delete
    void deleteUser(String id);

    List<User> allUsers();

    RestaurantDto getRestaurantByAdmin(String adminId);

}
