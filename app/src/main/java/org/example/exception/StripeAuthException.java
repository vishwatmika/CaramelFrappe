package org.example.exception;

import com.stripe.exception.StripeException;

public class StripeAuthException extends StripeClientException {
    public StripeAuthException(String message, StripeException stripeException) {
        super(message, stripeException);
    }
}
