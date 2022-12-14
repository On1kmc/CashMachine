package com.example.CashMashine.utils;

public enum Operation {

    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT,
    BALANCE;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        switch (i) {
            case 1:
                return Operation.INFO;
            case 2:
                return Operation.BALANCE;
            case 3:
                return Operation.DEPOSIT;
            case 4:
                return Operation.WITHDRAW;
            case 5:
                return Operation.EXIT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
