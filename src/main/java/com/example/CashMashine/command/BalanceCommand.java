package com.example.CashMashine.command;

import com.example.CashMashine.exception.InterruptOperationException;
import com.example.CashMashine.models.Bill;
import com.example.CashMashine.models.Card;
import com.example.CashMashine.services.ConsoleHelper;
import com.example.CashMashine.services.SessionService;
import com.example.CashMashine.utils.BundleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class BalanceCommand implements Command {

    @BundleResource(path = "locale.balance")
    private ResourceBundleMessageSource res;
    private final SessionService sessionService;

    private final ConsoleHelper consoleHelper;

    @Autowired
    public BalanceCommand(SessionService sessionService, ConsoleHelper consoleHelper) {
        this.sessionService = sessionService;
        this.consoleHelper = consoleHelper;
    }

    @Override
    public void execute() throws InterruptOperationException {
        Card card = sessionService.getInitializeCard();
        List<Bill> billList = card.getBillList();
        consoleHelper.writeMessage(res.getMessage("balance.info", new Object[]{}, Locale.getDefault()));
        billList.forEach(bill -> {
            consoleHelper.writeMessage(bill.getBalance() + " " + bill.getCurrency());
        });

    }
}
