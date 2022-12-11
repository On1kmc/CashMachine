package com.example.CashMashine.command;

import com.example.CashMashine.Operation;
import com.example.CashMashine.exception.InterruptOperationException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandExecutor {

    private final DepositCommand depositCommand;
    private final ExitCommand exitCommand;
    private final LoginCommand loginCommand;

    private final InfoCommand infoCommand;

    private final WithdrawCommand withdrawCommand;

    @Autowired
    public CommandExecutor(DepositCommand depositCommand, ExitCommand exitCommand, LoginCommand loginCommand, InfoCommand infoCommand, WithdrawCommand withdrawCommand) {
        this.depositCommand = depositCommand;
        this.exitCommand = exitCommand;
        this.loginCommand = loginCommand;
        this.infoCommand = infoCommand;
        this.withdrawCommand = withdrawCommand;
    }

    private final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

    @PostConstruct
    public void init() {
        allKnownCommandsMap.put(Operation.DEPOSIT, depositCommand);
        allKnownCommandsMap.put(Operation.LOGIN, loginCommand);
        allKnownCommandsMap.put(Operation.EXIT, exitCommand);
        allKnownCommandsMap.put(Operation.INFO, infoCommand);
        allKnownCommandsMap.put(Operation.WITHDRAW, withdrawCommand);
    }


    public void execute(Operation operation) throws InterruptOperationException {
        allKnownCommandsMap.get(operation).execute();
    }

}
