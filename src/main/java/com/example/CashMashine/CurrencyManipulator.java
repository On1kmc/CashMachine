package com.example.CashMashine;


import com.example.CashMashine.exception.NotEnoughMoneyException;
import com.example.CashMashine.models.Banknote;
import com.example.CashMashine.repositories.BanknoteRepo;
import com.example.CashMashine.repositories.ManipulatorRepo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CurrencyManipulator {

    private final ManipulatorRepo manipulatorRepo;

    private final BanknoteRepo banknoteRepo;

    private final ConsoleHelper consoleHelper;

    public CurrencyManipulator(ManipulatorRepo manipulatorRepo, BanknoteRepo banknoteRepo, ConsoleHelper consoleHelper) {
        this.manipulatorRepo = manipulatorRepo;
        this.banknoteRepo = banknoteRepo;
        this.consoleHelper = consoleHelper;
    }

    //private Map<Integer, Integer> denominations;


    @Transactional
    public void addAmount(String currency, int denomination, int count) {
        Banknote banknote = banknoteRepo.findAllByCurrencyAndNominal(currency, denomination);
        banknote.setCount(banknote.getCount() + count);
        banknoteRepo.save(banknote);
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
