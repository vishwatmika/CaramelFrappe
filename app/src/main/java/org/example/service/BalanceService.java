package org.example.service;

import com.stripe.model.Balance;
import org.example.repository.StripeClient;

public class BalanceService {
    private final StripeClient client;

    public BalanceService(StripeClient client) {
        this.client = client;
    }

    public String getAvailableBalance() {
        Balance balance = client.getBalance();
        return String.format(
            "Available Balance: %s %s",
            balance.getAvailable().getFirst().getAmount(),
            balance.getAvailable().getFirst().getCurrency()
        );
    }

    public String getPendingBalance() {
        Balance balance = client.getBalance();
        return String.format(
            "Pending Balance: %s %s",
            balance.getPending().getFirst().getAmount(),
            balance.getPending().getFirst().getCurrency()
        );
    }
}
