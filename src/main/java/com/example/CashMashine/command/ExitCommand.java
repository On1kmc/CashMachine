package com.example.CashMashine.command;

import com.example.CashMashine.exception.LogoutOperationException;
import com.example.CashMashine.services.ConsoleHelper;
import com.example.CashMashine.services.SessionService;
import com.example.CashMashine.utils.BundleResource;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;


import java.util.Locale;


@Component
public class ExitCommand implements Command {

    @BundleResource(name = "locale.exit")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    private final ConsoleHelper consoleHelper;

    private final SessionService sessionService;

    @Autowired
    public ExitCommand(ConsoleHelper consoleHelper, SessionService sessionService) {
        this.consoleHelper = consoleHelper;
        this.sessionService = sessionService;
    }

    @Override
    public void execute() throws InterruptOperationException, LogoutOperationException {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("exit.question.y.n", new Object[]{}, Locale.US));
        try {
            if (consoleHelper.readString().equalsIgnoreCase("y")) {
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("thank.message", new Object[]{}, Locale.US));
                sessionService.setCard(null);
                throw new LogoutOperationException();
            }
        } catch (CanceledOperationException ignored) {
        }
    }
}
