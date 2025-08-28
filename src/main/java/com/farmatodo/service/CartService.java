package com.farmatodo.service;

import com.farmatodo.dto.CartCheckoutDTO;
import com.farmatodo.dto.CartItemDTO;
import com.farmatodo.entity.Order;

public interface CartService {

    void addItem(Long customerId, CartItemDTO item);

    void removeItem(Long customerId, Long productId);

    Order checkout(CartCheckoutDTO checkoutDTO);
}
