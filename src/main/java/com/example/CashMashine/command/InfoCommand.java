package com.example.CashMashine.command;

import com.example.CashMashine.ConsoleHelper;
import com.example.CashMashine.CurrencyManipulatorService;
import com.example.CashMashine.annotation.BundleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;


import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class InfoCommand implements Command {

    private final ConsoleHelper consoleHelper;

    private final CurrencyManipulatorService currencyManipulator;

    @BundleResource(name = "locale.info")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public InfoCommand(ConsoleHelper consoleHelper, CurrencyManipulatorService currencyManipulator) {
        this.consoleHelper = consoleHelper;
        this.currencyManipulator = currencyManipulator;
    }

    @Override
    public void execute() {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("before", new Object[]{}, Locale.getDefault()));
        AtomicBoolean hasMoney = new AtomicBoolean(false);
        currencyManipulator.getAllManipulators()
                .forEach(s ->  {
                    boolean b = s.getBanknotes().stream().anyMatch(banknote -> banknote.getCount() > 0);
                    if (b) {
                            consoleHelper.writeMessage(s.getCurrency() + " - " + currencyManipulator.getTotalAmount(s.getCurrency()));
                            hasMoney.set(true);
                        }
                });
        if (!hasMoney.get()) {
            consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("no.money", new Object[]{}, Locale.getDefault()));
        }
    }
}
