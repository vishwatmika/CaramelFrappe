package org.example.exception;

import com.stripe.exception.StripeException;

public class StripeGenericException extends StripeClientException {
    public StripeGenericException(String message, StripeException stripeException) {
        super(message, stripeException);
    }
}
