package org.example.exception;

import com.stripe.exception.StripeException;

public class StripeBadRequestException extends StripeClientException {
    public StripeBadRequestException(String message, StripeException stripeException) {
        super(message, stripeException);
    }

    public StripeBadRequestException(String message, int statusCode) {
        super(message, statusCode);
    }
}
