package com.cilut.coreapi.controller;

import com.cilut.coreapi.entity.CustomerOrder;
import com.cilut.coreapi.request.OrderRequest;
import com.cilut.coreapi.dto.CustomerOrderDto;
import com.cilut.coreapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core-api/api/users/admin")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    @MessageMapping("/placeOrder")
    @SendTo("/topic/orders")
    public List<OrderRequest> orderRequest(@RequestBody List<OrderRequest> orders){
        List<CustomerOrder> customerOrderList = this.orderService.saveOrders(orders);
        System.err.println(customerOrderList);
        return orders;
    }

    @GetMapping("/orders/{id}")
    public List<CustomerOrderDto> getOrders(@PathVariable String id){
        return this.orderService.getOrdersByRestaurant(id);
    }

}
