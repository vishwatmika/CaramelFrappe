package org.example.repository;

import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import com.stripe.model.Customer;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import org.example.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

import static org.example.repository.ClientKey.API_KEY;

public class StripeClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(StripeClient.class);
    private RequestOptions requestOptions;

    public StripeClient() {
        this.requestOptions = RequestOptions.builder()
            .setApiKey(API_KEY.getValue())
            .build();
    }

    public Balance getBalance() {
        return executeStripeApiCall(() -> Balance.retrieve(this.requestOptions), "getBalance");
    }

    public Customer createCustomer(CustomerCreateParams params) {
        return executeStripeApiCall(() -> Customer.create(params, this.requestOptions), "createCustomer");
    }

    public Customer retrieveCustomer(String customerId) {
        return executeStripeApiCall(() -> Customer.retrieve(customerId, this.requestOptions), "retrieveCustomer");
    }

    // Helper method to encapsulate API call and exception handling
    private <T> T executeStripeApiCall(Callable<T> apiCall, String operationName) throws StripeClientException {
        try {
            return apiCall.call();
        } catch (StripeException e) {
            LOGGER.error("Stripe API call failed for operation '{}' with status {}: {}",
                    operationName, e.getStatusCode(), e.getMessage(), e);

            switch (e.getStatusCode()) {
                case 400:
                    throw new StripeBadRequestException("Bad request to Stripe API: " + e.getMessage(), e);
                case 401:
                case 403:
                    throw new StripeAuthException("Authentication/permission error with Stripe API: " + e.getMessage(), e);
                case 404:
                    throw new StripeNotFoundException("Resource not found in Stripe API: " + e.getMessage(), e);
                case 429:
                    throw new StripeThrottlingException("Too many requests", e);
                    // implement exponential backoff and retry
                case 500:
                case 502:
                case 503:
                case 504:
                    throw new StripeInternalServerException("Stripe API server error: " + e.getMessage(), e);
                default:
                    // For any unhandled status codes or general StripeException
                    throw new StripeGenericException("Unknown Stripe API error: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            // Catch any other unexpected runtime exceptions from the Callable itself
            LOGGER.error("Unexpected error during Stripe API call for operation '{}': {}", operationName, e.getMessage(), e);
            throw new StripeInternalServerException("Unexpected error during Stripe API call: " + e.getMessage(), (StripeException) e);
        }
    }
}
