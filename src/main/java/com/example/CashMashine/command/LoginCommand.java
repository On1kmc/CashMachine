package com.example.CashMashine.command;

import com.example.CashMashine.models.Card;
import com.example.CashMashine.services.ConsoleHelper;
import com.example.CashMashine.services.LoginService;
import com.example.CashMashine.services.SessionService;
import com.example.CashMashine.utils.BundleResource;
import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class LoginCommand implements Command {
    @BundleResource(path = "locale.login")
    private ResourceBundleMessageSource resourceBundleMessageSource;

    private final ConsoleHelper consoleHelper;

    private final LoginService loginService;

    private final SessionService sessionService;

    @Autowired
    public LoginCommand(ConsoleHelper consoleHelper, LoginService loginService, SessionService sessionService) {
        this.consoleHelper = consoleHelper;
        this.loginService = loginService;
        this.sessionService = sessionService;
    }

    @Override
    public void execute() throws InterruptOperationException {
        while (true) {
            consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("specify.data", new Object[]{}, Locale.getDefault()));
            String creditCardNumber;
            String pinStr;
            try {
                creditCardNumber = consoleHelper.readString();
                if (creditCardNumber == null || (creditCardNumber = creditCardNumber.trim()).length() != 12) {
                    consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("try.again.with.details", new Object[]{}, Locale.getDefault()));
                    continue;
                }
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("specify.pin", new Object[]{}, Locale.getDefault()));
                pinStr = consoleHelper.readString();
            } catch (CanceledOperationException e) {
                throw new InterruptOperationException();
            }

            consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("before", new Object[]{}, Locale.getDefault()));
            if (pinStr == null || (pinStr = pinStr.trim()).length() != 4) {
                consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("try.again.with.details", new Object[]{}, Locale.getDefault()));
            } else {
                long cardNumber = Long.parseLong(creditCardNumber);
                Card card = loginService.findByCardNumber(cardNumber);
                if (card != null && card.getPinCode() == Integer.parseInt(pinStr)) {
                    consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("success.format", new Object[]{creditCardNumber}, Locale.getDefault()));
                    sessionService.setCard(card);
                    break;
                } else {
                    consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("not.verified.format", new Object[]{creditCardNumber}, Locale.getDefault()));
                    consoleHelper.writeMessage(resourceBundleMessageSource.getMessage("try.again.or.exit", new Object[]{}, Locale.getDefault()));
                }
            }
        }
    }
}
