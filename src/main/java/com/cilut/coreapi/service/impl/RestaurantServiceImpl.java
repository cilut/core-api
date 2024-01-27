package com.cilut.coreapi.service.impl;

import com.cilut.coreapi.response.RestaurantResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cilut.coreapi.dto.RestaurantDto;
import com.cilut.coreapi.entity.Address;
import com.cilut.coreapi.entity.Restaurant;
import com.cilut.coreapi.exceptions.ResourceNotFoundException;
import com.cilut.coreapi.repository.RestaurantRepository;
import com.cilut.coreapi.service.RestaurantService;
import com.cilut.coreapi.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestTemplate restTemplate;
    private final RestaurantRepository restaurantRepository;

    @Value("${google.maps.api.key}")
    private String API_KEY;

    @Override
    @CacheEvict(value = "allRestaurants", allEntries = true)
    public void saveRestaurant(Restaurant restaurant) {

        Address address = restaurant.getAddress();

        // Construct the query string for the Place API
        String fullApiUrl = getFullApiUrl(address);
        System.err.println(fullApiUrl);

        // Make the API request and get the JSON response
        String response = this.restTemplate.getForObject(fullApiUrl, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode results = rootNode.get("results");
            if (results.isArray() && !results.isEmpty()) {
                JsonNode location = results.get(0).get("geometry").get("location");

                double latitude = location.get("lat").asDouble();
                double longitude = location.get("lng").asDouble();

                restaurant.setLatitude(latitude);
                restaurant.setLongitude(longitude);

            } else {
                System.err.println("No results found in the response.");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        this.restaurantRepository.save(restaurant);
    }

    private String getFullApiUrl(Address address) {

        String fullLocation = address.getStreetAddress().trim() +" "+ address.getPostal().trim();
        String locationQuery = fullLocation.replaceAll(",", " ").replaceAll(" ", "+");

        // Google Maps Place API URL
        String placeApiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=";

        // Construct the full API URL
        return placeApiUrl.concat(locationQuery).concat("&key=").concat(API_KEY);
    }


    @Override
    public RestaurantDto findRestaurantById(String id){
        Restaurant restaurant = this.restaurantRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not FOUND with this id: " + id));

        return ModelMapper.restaurantToDto(restaurant);
    }

    @Override
    //@Cacheable(value = "allRestaurants")
    public RestaurantResponse getAllRestaurant(int pageNumber, int pageSize){

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<Restaurant> restaurantPage = this.restaurantRepository.findAll(pageRequest);

        List<Restaurant> allRestaurants = restaurantPage.getContent();

        List<RestaurantDto> restaurantDtoList = ModelMapper.restaurantListToDtoList(allRestaurants);

        return new RestaurantResponse(
                restaurantDtoList,
                restaurantPage.getNumber(),
                restaurantPage.getSize(),
                restaurantPage.getTotalElements(),
                restaurantPage.getTotalPages(),
                restaurantPage.isLast());

    }

    @Override
    public List<RestaurantDto> findNearRestaurant(double latitude, double longitude, double radius) {
        List<Restaurant> restaurantsNearby = this.restaurantRepository.findRestaurantsNearby(latitude, longitude, radius);
        return ModelMapper.restaurantListToDtoList(restaurantsNearby);
    }

}
