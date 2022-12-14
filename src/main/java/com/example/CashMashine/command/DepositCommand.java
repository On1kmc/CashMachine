package com.example.CashMashine.command;



import com.example.CashMashine.services.BillService;
import com.example.CashMashine.services.ConsoleHelper;
import com.example.CashMashine.services.CurrencyManipulatorService;
import com.example.CashMashine.utils.BundleResource;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class DepositCommand implements Command {

    @BundleResource(path = "locale.deposit")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    private final ConsoleHelper consoleHelper;

    private final CurrencyManipulatorService currencyManipulator;

    private final BillService billService;

    @Autowired
    public DepositCommand(ConsoleHelper consoleHelper, CurrencyManipulatorService currencyManipulator, BillService billService) {
        this.consoleHelper = consoleHelper;
        this.currencyManipulator = currencyManipulator;
        this.billService = billService;
    }




    @Override
    public void execute() throws InterruptOperationException {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("common.before", new Object[] {}, Locale.getDefault()));
        try {
            String currencyCode = consoleHelper.askCurrencyCode();
            String[] digits = consoleHelper.getValidTwoDigits();
            int denomination = Integer.parseInt(digits[0]);
            int count = Integer.parseInt(digits[1]);
            int cashForDeposit = denomination * count;
            currencyManipulator.addAmount(currencyCode, denomination, count);
            billService.depositCash(cashForDeposit, currencyCode);
            consoleHelper.writeMessage(String.format(resourceBundleMessageSource.getMessage("success.format", new Object[]{}, Locale.US), (denomination * count), currencyCode));
        } catch (CanceledOperationException e) {
            consoleHelper.writeMessage("Operation canceled");
        }
    }
}
