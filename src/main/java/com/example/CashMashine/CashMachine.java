package com.example.CashMashine;

import com.example.CashMashine.command.CommandExecutor;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Locale;


public class CashMachine implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private CommandExecutor commandExecutor;
    @Autowired
    private ConsoleHelper consoleHelper;


    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Locale.setDefault(Locale.ENGLISH);
        Operation operation;
        while (true) {
            try {
                commandExecutor.execute(Operation.LOGIN);
                do {
                    operation = consoleHelper.askOperation();
                    commandExecutor.execute(operation);
                } while (operation != Operation.EXIT);
            } catch (InterruptOperationException | CanceledOperationException e) {
                consoleHelper.printExitMessage();
                System.exit(0);
            }
        }
    }
}
