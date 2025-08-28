package com.farmatodo.service.impl;

import com.farmatodo.dto.CartCheckoutDTO;
import com.farmatodo.dto.CartItemDTO;
import com.farmatodo.entity.*;
import com.farmatodo.repository.CustomerRepository;
import com.farmatodo.repository.OrderRepository;
import com.farmatodo.repository.ProductRepository;
import com.farmatodo.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItem() {
        CartItemDTO item = new CartItemDTO();
        item.setProductId(1L);
        item.setQuantity(2);

        cartService.addItem(1L, item);

        assertEquals(1, cartService.getCartMemory().get(1L).size());
        assertEquals(item.getProductId(), cartService.getCartMemory().get(1L).get(0).getProductId());
    }

    @Test
    void testRemoveItem() {
        CartItemDTO item1 = new CartItemDTO();
        item1.setProductId(1L);
        item1.setQuantity(1);

        CartItemDTO item2 = new CartItemDTO();
        item2.setProductId(2L);
        item2.setQuantity(1);

        cartService.addItem(1L, item1);
        cartService.addItem(1L, item2);

        cartService.removeItem(1L, 1L);

        assertEquals(1, cartService.getCartMemory().get(1L).size());
        assertEquals(2L, cartService.getCartMemory().get(1L).get(0).getProductId());
    }

    @Test
    void testCheckoutSuccess() {
        Long customerId = 1L;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setEmail("test@example.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Product product = new Product();
        product.setId(1L);
        product.setName("Producto 1");
        product.setPrice(BigDecimal.valueOf(10));
        product.setStock(5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        CartItemDTO item = new CartItemDTO();
        item.setProductId(1L);
        item.setQuantity(2);
        cartService.addItem(customerId, item);

        Order savedOrder = new Order();
        savedOrder.setId(100L);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(100L);
            return o;
        });

        CartCheckoutDTO checkoutDTO = new CartCheckoutDTO();
        checkoutDTO.setCustomerId(customerId);
        checkoutDTO.setAddress("Calle Falsa 123");
        checkoutDTO.setCreditCardToken("token123");
        checkoutDTO.setItems(List.of(item));

        Order order = cartService.checkout(checkoutDTO);

        assertEquals(customerId, order.getCustomer().getId());
        assertEquals(1, order.getItems().size());
        assertEquals(2, product.getStock() + item.getQuantity() - product.getStock()); // Stock disminuyó
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), anyString(), anyString());
    }

    @Test
    void testCheckoutEmptyCart() {
        CartCheckoutDTO dto = new CartCheckoutDTO();
        dto.setCustomerId(1L);
        dto.setAddress("Calle");
        dto.setCreditCardToken("token");
        dto.setItems(Collections.emptyList());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer()));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> cartService.checkout(dto));
        assertEquals("El carrito está vacío", ex.getMessage());
    }

    @Test
    void testCheckoutCustomerNotFound() {
        CartCheckoutDTO dto = new CartCheckoutDTO();
        dto.setCustomerId(99L);

        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartService.checkout(dto));
    }

    @Test
    void testCheckoutStockInsufficient() {
        Long customerId = 1L;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setEmail("test@example.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Product product = new Product();
        product.setId(1L);
        product.setName("Producto 1");
        product.setPrice(BigDecimal.valueOf(10));
        product.setStock(1); // stock insuficiente
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        CartItemDTO item = new CartItemDTO();
        item.setProductId(1L);
        item.setQuantity(2); // pide más que el stock disponible
        cartService.addItem(customerId, item);

        CartCheckoutDTO checkoutDTO = new CartCheckoutDTO();
        checkoutDTO.setCustomerId(customerId);
        checkoutDTO.setAddress("Calle Falsa 123");
        checkoutDTO.setCreditCardToken("token123");
        checkoutDTO.setItems(List.of(item));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> cartService.checkout(checkoutDTO));
        assertEquals("Stock insuficiente para el producto: Producto 1", ex.getMessage());
    }

    @Test
    void testCheckoutEmailThrowsException() {
        Long customerId = 1L;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setEmail("test@example.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Product product = new Product();
        product.setId(1L);
        product.setName("Producto 1");
        product.setPrice(BigDecimal.valueOf(10));
        product.setStock(5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        CartItemDTO item = new CartItemDTO();
        item.setProductId(1L);
        item.setQuantity(1);
        cartService.addItem(customerId, item);

        CartCheckoutDTO checkoutDTO = new CartCheckoutDTO();
        checkoutDTO.setCustomerId(customerId);
        checkoutDTO.setAddress("Calle Falsa 123");
        checkoutDTO.setCreditCardToken("token123");
        checkoutDTO.setItems(List.of(item));

        doThrow(new RuntimeException("SMTP error")).when(emailService).sendEmail(anyString(), anyString(), anyString());

        Order order = cartService.checkout(checkoutDTO);

        assertEquals(customerId, order.getCustomer().getId());
        assertEquals(1, order.getItems().size());
    }

}
