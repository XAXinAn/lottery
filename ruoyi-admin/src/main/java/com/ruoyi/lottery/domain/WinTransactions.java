package com.ruoyi.lottery.domain;

import java.math.BigDecimal;

public class WinTransactions {
    private Transactions transaction;
    private int time;

    public WinTransactions(Transactions transaction, int time) {
        this.transaction = transaction;
        this.time = time;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
