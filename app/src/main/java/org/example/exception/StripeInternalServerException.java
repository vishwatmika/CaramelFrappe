package org.example.exception;

import com.stripe.exception.StripeException;

public class StripeInternalServerException extends StripeClientException {
    public StripeInternalServerException(String message, StripeException stripeException) {
        super(message, stripeException);
    }
}
