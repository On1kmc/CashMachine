package com.example.CashMashine.command;



import com.example.CashMashine.ConsoleHelper;
import com.example.CashMashine.CurrencyManipulator;
import com.example.CashMashine.CurrencyManipulatorFactory;
import com.example.CashMashine.annotation.BundleResource;
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

    @Autowired
    public DepositCommand(ConsoleHelper consoleHelper) {
        this.consoleHelper = consoleHelper;
    }

    public void setResourceBundleMessageSource(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }



    @Override
    public void execute() throws InterruptOperationException {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("common.before", new Object[] {}, Locale.US));
        String currencyCode = consoleHelper.askCurrencyCode();
        String[] digits = consoleHelper.getValidTwoDigits(currencyCode);
        int denomination = Integer.parseInt(digits[0]);
        int count = Integer.parseInt(digits[1]);

        CurrencyManipulator manipulatorByCurrencyCode = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        manipulatorByCurrencyCode.addAmount(denomination, count);
        consoleHelper.writeMessage(String.format(resourceBundleMessageSource.getMessage("success.format", new Object[]{}, Locale.US), (denomination * count), currencyCode));

    }
}
