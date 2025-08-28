package com.farmatodo.controller;

import com.farmatodo.dto.CustomerDTO;
import com.farmatodo.dto.CustomerResponseDTO;
import com.farmatodo.entity.Customer;
import com.farmatodo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        Customer saved = customerService.registerCustomer(customer);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> getByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerResponseDTO> dtos = customers.stream().map(c -> {
            CustomerResponseDTO dto = new CustomerResponseDTO();
            dto.setId(c.getId());
            dto.setName(c.getName());
            dto.setEmail(c.getEmail());
            dto.setPhone(c.getPhone());
            dto.setAddress(c.getAddress());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
    }

}
