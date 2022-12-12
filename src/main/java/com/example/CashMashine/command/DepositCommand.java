package com.example.CashMashine.command;



import com.example.CashMashine.ConsoleHelper;
import com.example.CashMashine.CurrencyManipulator;
import com.example.CashMashine.annotation.BundleResource;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class DepositCommand implements Command {

    @BundleResource(name = "locale.deposit")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    private final ConsoleHelper consoleHelper;

    private final CurrencyManipulator currencyManipulator;

    @Autowired
    public DepositCommand(ConsoleHelper consoleHelper, CurrencyManipulator currencyManipulator) {
        this.consoleHelper = consoleHelper;
        this.currencyManipulator = currencyManipulator;
    }




    @Override
    public void execute() throws InterruptOperationException {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("common.before", new Object[] {}, Locale.US));
        try {
            String currencyCode = consoleHelper.askCurrencyCode();
            String[] digits = consoleHelper.getValidTwoDigits();
            int denomination = Integer.parseInt(digits[0]);
            int count = Integer.parseInt(digits[1]);
            currencyManipulator.addAmount(currencyCode, denomination, count);
            consoleHelper.writeMessage(String.format(resourceBundleMessageSource.getMessage("success.format", new Object[]{}, Locale.US), (denomination * count), currencyCode));
        } catch (CanceledOperationException e) {
            consoleHelper.writeMessage("Operation canceled");
        }
    }
}
