package org.example.exception;

import com.stripe.exception.StripeException;

public class StripeThrottlingException extends StripeClientException {
    public StripeThrottlingException(String message, StripeException stripeException) {
        super(message, stripeException);
    }
}
