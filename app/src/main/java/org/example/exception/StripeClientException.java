package org.example.exception;

import com.stripe.exception.StripeException;

// https://docs.stripe.com/error-low-level
// 200	OK	Everything worked as expected.
// 400	Bad Request	The request was unacceptable, often due to missing a required parameter.
// 401	Unauthorized	No valid API key provided.
// 402	Request Failed	The parameters were valid but the request failed.
// 403	Forbidden	The API key doesn’t have permissions to perform the request.
// 409	Conflict	The request conflicts with another request (perhaps due to using the same idempotent key).
// 424	External Dependency Failed	The request couldn’t be completed due to a failure in a dependency external to Stripe.
// 429	Too Many Requests	Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.
// 500, 502, 503, 504	Server Errors	Something went wrong on Stripe’s end.
public abstract class StripeClientException extends RuntimeException {
    private final StripeException stripeException;
    private final int statusCode;

    public StripeClientException(String message, int statusCode) {
        super(message);
        this.stripeException = null;
        this.statusCode = statusCode;
    }

    public StripeClientException(String message, StripeException stripeException) {
        super(message, stripeException);
        this.stripeException = stripeException;
        this.statusCode = stripeException.getStatusCode();
    }

    public StripeException getStripeException() {
        return stripeException;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

