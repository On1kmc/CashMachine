package com.example.CashMashine.command;

import com.example.CashMashine.ConsoleHelper;
import com.example.CashMashine.CurrencyManipulatorFactory;
import com.example.CashMashine.annotation.BundleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;


import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class InfoCommand implements Command {

    private final ConsoleHelper consoleHelper;

    @BundleResource(name = "locale.info")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public InfoCommand(ConsoleHelper consoleHelper) {
        this.consoleHelper = consoleHelper;
    }

    @Override
    public void execute() {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("before", new Object[]{}, Locale.US));
        AtomicBoolean hasMoney = new AtomicBoolean(false);
        CurrencyManipulatorFactory.getAllCurrencyManipulators()
                .forEach(s ->  {
                    if (s.getTotalAmount() != 0) {
                        consoleHelper.writeMessage(s.getCurrencyCode() + " - " + s.getTotalAmount());
                        hasMoney.set(true);
                    }
                });
        if (!hasMoney.get()) {
            consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("no.money", new Object[]{}, Locale.US));
        }
    }
}
