package com.example.CashMashine.command;

import com.example.CashMashine.services.ConsoleHelper;
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

    @Autowired
    public ExitCommand(ConsoleHelper consoleHelper) {
        this.consoleHelper = consoleHelper;
    }

    @Override
    public void execute() throws InterruptOperationException {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("exit.question.y.n", new Object[]{}, Locale.US));
        try {
            if (consoleHelper.readString().equalsIgnoreCase("y")) {
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("thank.message", new Object[]{}, Locale.US));
            } else {
            }
        } catch (CanceledOperationException ignored) {
        }
    }
}
