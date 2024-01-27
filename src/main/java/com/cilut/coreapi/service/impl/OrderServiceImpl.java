package com.cilut.coreapi.service.impl;

import com.cilut.coreapi.entity.CustomerOrder;
import com.cilut.coreapi.entity.User;
import com.cilut.coreapi.exceptions.UserNotFoundException;
import com.cilut.coreapi.repository.OrderRepository;
import com.cilut.coreapi.repository.UserRepository;
import com.cilut.coreapi.request.OrderRequest;
import com.cilut.coreapi.dto.CustomerOrderDto;
import com.cilut.coreapi.service.OrderService;
import com.cilut.coreapi.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public List<CustomerOrder> saveOrders(List<OrderRequest> orderList) {
        List<CustomerOrder> customerOrderList = new LinkedList<>();

        for (OrderRequest orderRequest : orderList){
            CustomerOrder customerOrder = ModelMapper.orderRequestToCustomerOrder(orderRequest);
            User user = this.userRepository.findById(orderRequest.userDto().id()).orElseThrow(() ->
                    new UserNotFoundException("User not found with this id!"));
            customerOrder.setUser(user);
            customerOrderList.add(customerOrder);
        }
        return this.orderRepository.saveAll(customerOrderList);
    }

    @Override
    public List<CustomerOrderDto> getOrdersByRestaurant(String restaurantId) {
        List<CustomerOrder> customerOrderList = this.orderRepository.findByRestaurantId(restaurantId);

        List<CustomerOrderDto> customerOrderDtoList = new LinkedList<>();
        for (CustomerOrder order : customerOrderList){
            CustomerOrderDto customerOrderDto = ModelMapper.customerOrderToResponse(order);
            customerOrderDtoList.add(customerOrderDto);
        }
        return customerOrderDtoList;
    }
}
