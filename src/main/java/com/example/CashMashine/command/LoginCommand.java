package com.example.CashMashine.command;

import com.example.CashMashine.ConsoleHelper;
import com.example.CashMashine.annotation.BundleResource;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import jakarta.annotation.PostConstruct;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;


@Component
public class LoginCommand implements Command {

    private ResourceBundle validCreditCards;
    @BundleResource(name = "locale.login")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    private final ConsoleHelper consoleHelper;

    public LoginCommand(ConsoleHelper consoleHelper) {
        this.consoleHelper = consoleHelper;
    }


    @PostConstruct
    public void init() {
         validCreditCards = ResourceBundle.getBundle("locale/verifiedCards", Locale.ENGLISH);
    }

    @Override
    public void execute() throws InterruptOperationException {
        while (true) {
            consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("specify.data", new Object[]{}, Locale.US));
            String creditCardNumber;
            String pinStr;
            try {
                creditCardNumber = consoleHelper.readString();
                pinStr = consoleHelper.readString();
            } catch (CanceledOperationException e) {
                throw new InterruptOperationException();
            }

            consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("before", new Object[]{}, Locale.US));
            if (creditCardNumber == null || (creditCardNumber = creditCardNumber.trim()).length() != 12 ||
                    pinStr == null || (pinStr = pinStr.trim()).length() != 4) {
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("try.again.with.details", new Object[]{}, Locale.US));
            } else {
                    if (validCreditCards.containsKey(creditCardNumber) && pinStr.equals(validCreditCards.getString(creditCardNumber))) {
                        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("success.format", new Object[]{creditCardNumber}, Locale.US));
                        break;
                    } else {
                        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("not.verified.format", new Object[]{creditCardNumber}, Locale.US));
                        consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("try.again.or.exit", new Object[]{}, Locale.US));
                    }
            }
        }
    }
}
