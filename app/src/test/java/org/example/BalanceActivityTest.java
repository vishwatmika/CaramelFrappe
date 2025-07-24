package org.example;

import com.stripe.model.Balance;
import com.stripe.model.Balance.Available;
import com.stripe.model.Balance.Pending;
import org.example.activity.BalanceActivity;
import org.example.repository.StripeClient;
import org.example.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BalanceActivityTest {
    private static BalanceActivity balanceActivity;
    private static BalanceService balanceService;

    @Mock
    StripeClient client;

    @Test
    void testGetAvailableBalanceSuccess() {
        Balance balance = new Balance();
        Available available = new Available();
        available.setAmount(100L);
        available.setCurrency("USD");

        balance.setAvailable(
            List.of(
                available
            )
        );
        Mockito.when(client.getBalance()).thenReturn(balance);

        balanceService = new BalanceService(client);
        balanceActivity = new BalanceActivity(balanceService);
        String availableBalance = balanceActivity.getAvailableBalance();
        assertEquals("Available Balance: 100 USD", availableBalance);
    }

    @Test
    void testGetPendingBalanceSuccess() {
        Balance balance = new Balance();
        Pending pending = new Pending();
        pending.setAmount(100L);
        pending.setCurrency("USD");

        balance.setPending(
                List.of(
                        pending
                )
        );

        Mockito.when(client.getBalance()).thenReturn(balance);

        balanceService = new BalanceService(client);
        balanceActivity = new BalanceActivity(balanceService);
        String pendingBalance = balanceActivity.getPendingBalance();
        assertEquals("Pending Balance: 100 USD", pendingBalance);
    }
}
