package com.farmatodo.service.impl;

import com.farmatodo.entity.Customer;
import com.farmatodo.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomerSuccess() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer saved = customerService.registerCustomer(customer);
        assertEquals(customer.getEmail(), saved.getEmail());
    }

    @Test
    void testRegisterCustomerEmailExists() {
        Customer customer = new Customer();
        customer.setEmail("existing@example.com");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(new Customer()));
        assertThrows(IllegalArgumentException.class, () -> customerService.registerCustomer(customer));
    }

    @Test
    void testGetCustomerByEmailSuccess() {
        Customer customer = new Customer();
        customer.setEmail("find@example.com");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        Customer found = customerService.getCustomerByEmail(customer.getEmail());
        assertEquals("find@example.com", found.getEmail());
    }

    @Test
    void testGetCustomerByEmailNotFound() {
        when(customerRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> customerService.getCustomerByEmail("missing@example.com"));
    }

    @Test
    void testGetAllCustomers() {
        Customer c1 = new Customer();
        Customer c2 = new Customer();
        when(customerRepository.findAll()).thenReturn(List.of(c1, c2));
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(2, customers.size());
    }

    @Test
    void testGetCustomerByIdSuccess() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer found = customerService.getCustomerById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(99L));
    }
}
