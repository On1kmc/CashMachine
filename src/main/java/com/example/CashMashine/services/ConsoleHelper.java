package com.example.CashMashine.services;


import com.example.CashMashine.utils.Operation;
import com.example.CashMashine.utils.BundleResource;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

@Component
@Data
public class ConsoleHelper {

    @BundleResource(path = "locale.common")
    private ResourceBundleMessageSource res;

    private final BufferedReader bis;

    private final CurrencyManipulatorService currencyManipulatorService;

    @Autowired
    public ConsoleHelper(BufferedReader bis, CurrencyManipulatorService currencyManipulatorService) {
        this.bis = bis;
        this.currencyManipulatorService = currencyManipulatorService;
    }

    public void writeMessage(String message) {
        System.out.println(message);
    }

    public String readString() throws InterruptOperationException, CanceledOperationException {
            try {
                String string = bis.readLine();
                if (string.equalsIgnoreCase("exit")) {
                    throw new InterruptOperationException();
                }
                if (string.equalsIgnoreCase("cancel")) {
                    throw new CanceledOperationException();
                }
                return string;
            } catch (IOException ignored) {
            }
        return null;
    }

    public Operation askOperation() throws InterruptOperationException, CanceledOperationException {
        writeMessage(res.getMessage("choose.operation", new Object[]{}, Locale.getDefault()) +
                "\n 1 - " + res.getMessage("operation.INFO", new Object[]{}, Locale.getDefault()) +
                "\n 2 - " + res.getMessage("operation.balance", new Object[]{}, Locale.getDefault()) +
                "\n 3 - " + res.getMessage("operation.DEPOSIT", new Object[]{}, Locale.getDefault()) +
                "\n 4 - " + res.getMessage("operation.WITHDRAW", new Object[]{}, Locale.getDefault()) +
                "\n 5 - " + res.getMessage("operation.EXIT", new Object[]{}, Locale.getDefault()));
        while (true) {
            try {
                String s = readString().trim();
                return Operation.getAllowableOperationByOrdinal(Integer.parseInt(s));
            } catch (IllegalArgumentException e) {
                writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.getDefault()));
            }
        }
    }

    public String askCurrencyCode() throws InterruptOperationException, CanceledOperationException {
        writeMessage(res.getMessage("choose.currency.code", new Object[]{}, Locale.getDefault()));
        while (true) {
            String request = readString();
            if (request == null || request.length() != 3) {
                writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.getDefault()));
                continue;
            }
            if (!currencyManipulatorService.hasManipulator(request)) {
                writeMessage(res.getMessage("currency.not.exist", new Object[]{}, Locale.getDefault()));
                continue;
            }
            return request.toUpperCase();
        }
    }

    public String[] getValidTwoDigits(String currency) throws InterruptOperationException, CanceledOperationException {
        String[] operationParameters;
        writeMessage(res.getMessage("choose.denomination.and.count.format", new Object[]{currency}, Locale.getDefault()));
        while (true) {
            String query = readString();
            try {
                if ((operationParameters = query.split(" ")).length != 2) {
                    writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.getDefault()));
                    continue;
                }
                if (Integer.parseInt(operationParameters[0]) <= 0) {
                    writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.getDefault()));
                    continue;
                }
                if (Integer.parseInt(operationParameters[1]) <= 0) {
                    writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.getDefault()));
                    continue;
                }
            } catch (Exception e) {
                writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.getDefault()));
                continue;
            }
            return operationParameters;
        }
    }

    public void printExitMessage() {
        writeMessage(res.getMessage("the.end", new Object[]{}, Locale.getDefault()));
    }
}
