package com.farmatodo.service;

import com.farmatodo.dto.OrderResponseDTO;
import com.farmatodo.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(Long customerId, List<Long> productIds);
    void processPayment(Order order);
    Order getOrderById(Long orderId);

}