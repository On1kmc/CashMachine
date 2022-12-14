package com.example.CashMashine.utils;

import com.example.CashMashine.command.CommandExecutor;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import com.example.CashMashine.exception.LogoutOperationException;
import com.example.CashMashine.services.ConsoleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Locale;


public class CashMachineStarter implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private CommandExecutor commandExecutor;
    @Autowired
    private ConsoleHelper consoleHelper;


    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Operation operation;
        while (true) {
            try {
                commandExecutor.execute(Operation.LOGIN);
                while (true) {
                    operation = consoleHelper.askOperation();
                    commandExecutor.execute(operation);
                }
            } catch (InterruptOperationException | CanceledOperationException e) {
                consoleHelper.printExitMessage();
                System.exit(0);
            } catch (LogoutOperationException ignored) {
            }
        }
    }
}
