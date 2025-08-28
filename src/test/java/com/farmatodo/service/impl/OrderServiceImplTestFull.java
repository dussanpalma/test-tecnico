package com.farmatodo.service.impl;

import com.farmatodo.dto.OrderItemResponseDTO;
import com.farmatodo.dto.OrderResponseDTO;
import com.farmatodo.entity.Customer;
import com.farmatodo.entity.Order;
import com.farmatodo.entity.OrderItem;
import com.farmatodo.entity.Product;
import com.farmatodo.repository.CustomerRepository;
import com.farmatodo.repository.OrderRepository;
import com.farmatodo.repository.ProductRepository;
import com.farmatodo.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Juan");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Product product = new Product();
        product.setId(10L);
        product.setName("Paracetamol");
        product.setPrice(BigDecimal.valueOf(15.5));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        Order savedOrder = new Order();
        savedOrder.setId(100L);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(100L);
            return o;
        });

        OrderResponseDTO dto = orderService.createOrder(1L, List.of(10L));

        assertEquals(100L, dto.getId());
        assertEquals(1L, dto.getCustomerId());
        assertEquals("Juan", dto.getCustomerName());
        assertEquals("PENDING", dto.getStatus());
        assertEquals(1, dto.getItems().size());

        OrderItemResponseDTO itemDTO = dto.getItems().get(0);
        assertEquals(10L, itemDTO.getProductId());
        assertEquals("Paracetamol", itemDTO.getProductName());
        assertEquals(BigDecimal.valueOf(15.5), itemDTO.getPrice());
        assertEquals(1, itemDTO.getQuantity());
    }

    @Test
    void testCreateOrderCustomerNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(99L, List.of(1L)));
    }

    @Test
    void testProcessPaymentSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");

        Order order = new Order();
        order.setId(1L);
        order.setCustomer(customer);
        order.setStatus("PENDING");

        java.lang.reflect.Field field = OrderServiceImpl.class.getDeclaredField("rejectProbability");
        field.setAccessible(true);
        field.set(orderService, 0.0);

        java.lang.reflect.Field retries = OrderServiceImpl.class.getDeclaredField("maxRetries");
        retries.setAccessible(true);
        retries.set(orderService, 3);

        orderService.processPayment(order);

        assertEquals("PAID", order.getStatus());
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), contains("Pago exitoso"), anyString());
    }

    @Test
    void testProcessPaymentFailure() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");

        Order order = new Order();
        order.setId(1L);
        order.setCustomer(customer);
        order.setStatus("PENDING");

        java.lang.reflect.Field field = OrderServiceImpl.class.getDeclaredField("rejectProbability");
        field.setAccessible(true);
        field.set(orderService, 1.0);

        java.lang.reflect.Field retries = OrderServiceImpl.class.getDeclaredField("maxRetries");
        retries.setAccessible(true);
        retries.set(orderService, 2);

        orderService.processPayment(order);

        assertEquals("FAILED", order.getStatus());
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), contains("Pago fallido"), anyString());
    }

    @Test
    void testGetOrderByIdFound() {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order result = orderService.getOrderById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(99L));
    }
}
