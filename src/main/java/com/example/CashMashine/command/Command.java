package com.example.CashMashine.command;

import com.example.CashMashine.exception.InterruptOperationException;
import com.example.CashMashine.exception.LogoutOperationException;

interface Command {

    void execute() throws InterruptOperationException, LogoutOperationException;
}
