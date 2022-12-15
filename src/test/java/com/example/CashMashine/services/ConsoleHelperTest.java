package com.example.CashMashine.services;

import com.example.CashMashine.exception.CanceledOperationException;
import com.example.CashMashine.exception.InterruptOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
class ConsoleHelperTest {

    @MockBean
    private BufferedReader bis;


    private ResourceBundleMessageSource res;

    @MockBean
    private CurrencyManipulatorService currencyManipulatorService;


    private ConsoleHelper consoleHelper;

    @BeforeEach
    void initialize() {
        consoleHelper = new ConsoleHelper(bis, currencyManipulatorService);
        res = Mockito.mock(ResourceBundleMessageSource.class);
    }

    @Test
    void readString() throws IOException {
        BDDMockito.given(bis.readLine()).willReturn("exit");
        Assertions.assertThrows(InterruptOperationException.class, () -> {
           consoleHelper.readString();
        });
        BDDMockito.given(bis.readLine()).willReturn("cancel");
        Assertions.assertThrows(CanceledOperationException.class, () -> {
            consoleHelper.readString();
        });
    }

    @Test
    @Disabled
    void askOperation() throws CanceledOperationException, InterruptOperationException, IOException {
        BDDMockito.given(bis.readLine()).willReturn("2");

        consoleHelper.askOperation();
    }

    @Test
    @Disabled
    void askCurrencyCode() {

    }

    @Test
    @Disabled
    void getValidTwoDigits() {
    }
}