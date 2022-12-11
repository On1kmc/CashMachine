package com.example.CashMashine.command;

import com.example.CashMashine.ConsoleHelper;
import com.example.CashMashine.annotation.BundleResource;
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


    public void setResourceBundleMessageSource(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }
    @Override
    public void execute() throws InterruptOperationException {
        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("exit.question.y.n", new Object[]{}, Locale.US));
        if (consoleHelper.readString().toLowerCase().equals("y")) {
            consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("thank.message", new Object[]{}, Locale.US));
        } else {
        }
    }
}
