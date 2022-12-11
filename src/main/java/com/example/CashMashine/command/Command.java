package com.example.CashMashine.command;

import com.example.CashMashine.exception.InterruptOperationException;

interface Command {

    void execute() throws InterruptOperationException;
}
