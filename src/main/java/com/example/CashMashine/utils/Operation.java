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
                return Operation.DEPOSIT;
            case 3:
                return Operation.WITHDRAW;
            case 4:
                return Operation.EXIT;
            case 5:
                return Operation.BALANCE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
