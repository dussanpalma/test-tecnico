package com.farmatodo.service;
import com.farmatodo.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    Customer getCustomerByEmail(String email);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);
}