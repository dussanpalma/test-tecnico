package com.farmatodo.controller;

import com.farmatodo.dto.CustomerDTO;
import com.farmatodo.dto.CustomerResponseDTO;
import com.farmatodo.entity.Customer;
import com.farmatodo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    private CustomerService customerService;
    private CustomerController controller;

    @BeforeEach
    void setUp() {
        customerService = mock(CustomerService.class);
        controller = new CustomerController(customerService);
    }

    @Test
    void testRegister() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setPhone("1234567890");
        dto.setAddress("123 Street");

        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        Customer saved = new Customer();
        saved.setId(1L);
        saved.setName(dto.getName());
        saved.setEmail(dto.getEmail());
        saved.setPhone(dto.getPhone());
        saved.setAddress(dto.getAddress());

        when(customerService.registerCustomer(any(Customer.class))).thenReturn(saved);

        ResponseEntity<Customer> response = controller.register(dto);

        verify(customerService).registerCustomer(any(Customer.class));
        assertNotNull(response);
        assertEquals(saved, response.getBody());
    }

    @Test
    void testGetByEmail() {
        String email = "john.doe@example.com";

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail(email);
        customer.setPhone("1234567890");
        customer.setAddress("123 Street");

        when(customerService.getCustomerByEmail(email)).thenReturn(customer);

        ResponseEntity<CustomerResponseDTO> response = controller.getByEmail(email);

        verify(customerService).getCustomerByEmail(email);

        CustomerResponseDTO dto = response.getBody();
        assertNotNull(dto);
        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getName(), dto.getName());
        assertEquals(customer.getEmail(), dto.getEmail());
        assertEquals(customer.getPhone(), dto.getPhone());
        assertEquals(customer.getAddress(), dto.getAddress());
    }

    @Test
    void testGetAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setPhone("1234567890");
        customer1.setAddress("123 Street");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setPhone("0987654321");
        customer2.setAddress("456 Avenue");

        when(customerService.getAllCustomers()).thenReturn(List.of(customer1, customer2));

        ResponseEntity<List<CustomerResponseDTO>> response = controller.getAllCustomers();

        verify(customerService).getAllCustomers();

        List<CustomerResponseDTO> dtos = response.getBody();
        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(customer1.getId(), dtos.get(0).getId());
        assertEquals(customer1.getName(), dtos.get(0).getName());
        assertEquals(customer2.getId(), dtos.get(1).getId());
        assertEquals(customer2.getName(), dtos.get(1).getName());
    }
}
