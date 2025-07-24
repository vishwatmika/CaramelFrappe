package org.example.exception;

import com.stripe.exception.StripeException;

public class StripeNotFoundException extends StripeClientException {
    public StripeNotFoundException(String message, StripeException stripeException) {
        super(message, stripeException);
    }
}
