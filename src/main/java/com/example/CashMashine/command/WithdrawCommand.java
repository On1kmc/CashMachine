package com.example.CashMashine.command;

import com.example.CashMashine.ConsoleHelper;
import com.example.CashMashine.CurrencyManipulator;
import com.example.CashMashine.annotation.BundleResource;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import com.example.CashMashine.exception.NotEnoughMoneyException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
public class WithdrawCommand implements Command {

    private final ConsoleHelper consoleHelper;

    private final CurrencyManipulator currencyManipulator;

    @BundleResource(name = "locale.withdraw")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    public WithdrawCommand(ConsoleHelper consoleHelper, CurrencyManipulator currencyManipulator) {
        this.consoleHelper = consoleHelper;
        this.currencyManipulator = currencyManipulator;
    }


    @Override
    public void execute() throws InterruptOperationException {
        while (true) {
            String currencyCode;
            String s;
            try {
                currencyCode = consoleHelper.askCurrencyCode();
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("specify.amount", new Object[]{}, Locale.US));
                s = consoleHelper.readString();
            } catch (CanceledOperationException e) {
                consoleHelper.writeMessage("Operation canceled");
                break;
            }
            try {
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("before", new Object[]{}, Locale.US));
                int count = Integer.parseInt(s);
                if (!currencyManipulator.isAmountAvailable(count, currencyCode)) {
                    consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("not.enough.money", new Object[]{}, Locale.US));
                    continue;
                }
                try {
                    Map<Integer, Integer> withdrawAmount = currencyManipulator.withdrawAmount(count, currencyCode);
                    withdrawAmount.forEach((key, value) -> consoleHelper.writeMessage("\t" + key + " - " + value));
                    consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("success.format", new Object[]{}, Locale.US));
                    break;
                } catch (NotEnoughMoneyException e) {
                    consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("exact.amount.not.available", new Object[]{}, Locale.US));
                }
            } catch (NumberFormatException e) {
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("specify.not.empty.amount", new Object[]{}, Locale.US));
            }
        }

    }
}
