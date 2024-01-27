package com.cilut.coreapi.service;

import com.cilut.coreapi.entity.CustomerOrder;
import com.cilut.coreapi.request.OrderRequest;
import com.cilut.coreapi.dto.CustomerOrderDto;

import java.util.List;

public interface OrderService {
    List<CustomerOrder> saveOrders(List<OrderRequest> orders);
    List<CustomerOrderDto> getOrdersByRestaurant(String restaurantId);
}
