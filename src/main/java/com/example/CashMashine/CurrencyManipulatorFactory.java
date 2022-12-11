package com.example.CashMashine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {};

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        if (map.containsKey(currencyCode.toLowerCase())) {
            return map.get(currencyCode.toLowerCase());
        } else {
            CurrencyManipulator manipulator = new CurrencyManipulator(currencyCode);
            map.put(currencyCode.toLowerCase(), manipulator);
            return manipulator;
        }
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return map.values();
    }
}
