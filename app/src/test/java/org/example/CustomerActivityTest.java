package org.example;

import com.stripe.model.Customer;
import org.example.activity.CustomerActivity;
import org.example.exception.StripeBadRequestException;
import org.example.exception.StripeInternalServerException;
import org.example.exception.StripeNotFoundException;
import org.example.repository.StripeClient;
import org.example.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CustomerActivityTest {
    private static CustomerActivity customerActivity;
    private static CustomerService customerService;

    @Mock
    StripeClient stripeClient;

    @Test
    void testRaisesWhenNameIsNull() {
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeBadRequestException.class, () -> customerActivity.createCustomer(null, "example.com"));
    }

    @Test
    void testRaisesWhenNameIsEmpty() {
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeBadRequestException.class, () -> customerActivity.createCustomer("", "example.com"));
    }

    @Test
    void testRaisesWhenEmailIsNull() {
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeBadRequestException.class, () -> customerActivity.createCustomer("name", null));
    }

    @Test
    void testRaisesWhenEmailIsEmpty() {
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeBadRequestException.class, () -> customerActivity.createCustomer("name", ""));
    }

    @Test
    void testRaisesWhenCustomerIdIsNull() {
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeBadRequestException.class, () -> customerActivity.getCustomer(null));
    }

    @Test
    void testRaisesWhenCustomerIdIsEmpty() {
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeBadRequestException.class, () -> customerActivity.getCustomer(""));
    }

    @Test
    void testCreatesCustomer() {
        Customer customer = new Customer();

        Mockito.when(stripeClient.createCustomer(Mockito.any())).thenReturn(customer);

        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);
        assertEquals(customer, customerActivity.createCustomer("name", "example.com"));
    }

    @Test
    void testGetsCustomer() {
        Customer customer = new Customer();

        Mockito.when(stripeClient.retrieveCustomer(Mockito.any())).thenReturn(customer);

        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);
        assertEquals(customer, customerActivity.getCustomer("id"));
    }

    @Test
    void testReturnsNotFoundException() {
        Mockito.when(stripeClient.retrieveCustomer(Mockito.any())).thenThrow(StripeNotFoundException.class);
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeNotFoundException.class, () -> customerActivity.getCustomer("name"));
    }

    @Test
    void testReturnsInternalServerException() {
        Mockito.when(stripeClient.retrieveCustomer(Mockito.any())).thenThrow(StripeInternalServerException.class);
        customerService = new CustomerService(stripeClient);
        customerActivity = new CustomerActivity(customerService);

        assertThrows(StripeInternalServerException.class, () -> customerActivity.getCustomer("name"));
    }
}
