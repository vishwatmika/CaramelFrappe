package org.example.activity;

import com.stripe.model.Customer;
import org.example.service.CustomerService;

public class CustomerActivity {
    private final CustomerService customerService;

    public CustomerActivity(CustomerService service) {
        this.customerService = service;
    }

    public Customer createCustomer(String name, String email) {
        return customerService.createCustomer(name, email);
    }

    public Customer getCustomer(String customerId) {
        return customerService.retriveCustomer(customerId);
    }
}
