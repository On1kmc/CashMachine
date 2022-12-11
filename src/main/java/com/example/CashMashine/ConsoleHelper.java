package com.example.CashMashine;


import com.example.CashMashine.annotation.BundleResource;
import com.example.CashMashine.exception.InterruptOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

@Component
public class ConsoleHelper {

    @BundleResource(name = "locale.common")
    private ResourceBundleMessageSource res;

    private final BufferedReader bis;

    @Autowired
    public ConsoleHelper(BufferedReader bis) {
        this.bis = bis;
    }

    public void writeMessage(String message) {
        System.out.println(message);
    }

    public String readString() throws InterruptOperationException {
            try {
                String string = bis.readLine();
                if (string.toLowerCase().equals("exit")) {
                    throw new InterruptOperationException();
                }
                return string;
            } catch (IOException ignored) {
            }
        return null;
    }

    public Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getMessage("choose.operation", new Object[]{}, Locale.US) +
                "\n 1 - " + res.getMessage("operation.INFO", new Object[]{}, Locale.US) +
                "\n 2 - " + res.getMessage("operation.DEPOSIT", new Object[]{}, Locale.US) +
                "\n 3 - " + res.getMessage("operation.WITHDRAW", new Object[]{}, Locale.US) +
                "\n 4 - " + res.getMessage("operation.EXIT", new Object[]{}, Locale.US));
        while (true) {
            try {
                String s = readString().trim();
                return Operation.getAllowableOperationByOrdinal(Integer.parseInt(s));
            } catch (IllegalArgumentException e) {
                writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.US));
            }
        }
    }

    public String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getMessage("choose.currency.code", new Object[]{}, Locale.US));
        while (true) {
            String request = readString();
            if (request == null || request.length() != 3) {
                writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.US));
                continue;
            }
            return request.toUpperCase();
        }
    }

    public String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] operationParameters = null;
        writeMessage(res.getMessage("choose.denomination.and.count.format", new Object[]{}, Locale.US));
        while (true) {
            String query = readString();
            try {
                if ((operationParameters = query.split(" ")).length != 2) {
                    writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.US));
                    continue;
                }
                if (Integer.parseInt(operationParameters[0]) <= 0) {
                    writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.US));
                    continue;
                }
                if (Integer.parseInt(operationParameters[1]) <= 0) {
                    writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.US));
                    continue;
                }
            } catch (Exception e) {
                writeMessage(res.getMessage("invalid.data", new Object[]{}, Locale.US));
                continue;
            }
            return operationParameters;
        }
    }

    public void printExitMessage() {
        writeMessage(res.getMessage("the.end", new Object[]{}, Locale.US));
    }
}
