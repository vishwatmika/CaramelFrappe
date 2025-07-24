package org.example.service;

import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.example.exception.StripeBadRequestException;
import org.example.repository.StripeClient;

public class CustomerService {
    private final StripeClient client;

    public CustomerService(StripeClient client) {
        this.client = client;
    }

    public Customer createCustomer(String name, String email) {
        if (name == null || email == null || name.isEmpty() || email.isEmpty()) {
           throw new StripeBadRequestException("Name or Email is not provided correctly", 400);
        }

        CustomerCreateParams params = CustomerCreateParams.builder()
                .setName(name)
                .setEmail(email)
                .build();

        return client.createCustomer(params);
    }

    public Customer retriveCustomer(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            throw new StripeBadRequestException("CustomerId is not provided correctly", 400);
        }

        return client.retrieveCustomer(customerId);
    }
}
