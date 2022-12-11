package com.example.CashMashine;


import com.example.CashMashine.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CurrencyManipulator {


    private final String currencyCode;

    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        AtomicInteger sum = new AtomicInteger();
        denominations.entrySet().stream().map(s -> s.getValue() * s.getKey()).forEach(s -> sum.set(sum.get() + s));
        return sum.get();
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        List<Integer> list = new ArrayList<>(denominations.keySet());
        Collections.sort(list);
        Collections.reverse(list);
        Map<Integer, Integer> result = new HashMap<>();
        if (getNext(expectedAmount, result, list, 0)) {
            result.forEach((key, value) -> denominations.put(key, denominations.get(key) - value));
        } else {
            throw new NotEnoughMoneyException();
        }
        return result;
    }

    private boolean getNext(int expectedAmount, Map<Integer, Integer> result, List<Integer> list, int currentIndex) {
        boolean isFind = false;
        if (expectedAmount == 0) return true;
        int s;
        int newExpectedAmount;
        for (int i = currentIndex; i < list.size(); i++) {
            if ((s = list.get(i)) <= expectedAmount) {
                int maxCount = denominations.get(s);
                int needCount = expectedAmount/s;
                if (needCount > maxCount) {
                    result.put(s, maxCount);
                } else {
                    result.put(s, needCount);
                }
                newExpectedAmount = expectedAmount - result.get(s) * s;
                isFind = getNext(newExpectedAmount, result, list, i + 1);
                if (!isFind) {
                    result.remove(s);
                }
                return isFind;
            }


        }
        return isFind;
    }

}
