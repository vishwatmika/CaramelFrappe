package org.example.activity;

import org.example.service.BalanceService;

public class BalanceActivity {
    private final BalanceService balanceService;

    public BalanceActivity(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public String getAvailableBalance() {
        return balanceService.getAvailableBalance();
    }

    public String getPendingBalance() {
        return balanceService.getPendingBalance();
    }
}
