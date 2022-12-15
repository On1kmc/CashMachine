package com.example.CashMashine.services;

import com.example.CashMashine.models.Bill;
import com.example.CashMashine.models.Card;
import com.example.CashMashine.repositories.BillRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class BillServiceTest {

    @MockBean
    private  BillRepo billRepo;

    @MockBean
    private  SessionService sessionService;

    @InjectMocks
    private  BillService billService;



    @BeforeEach
    public void initialize() {
        billService = new BillService(billRepo, sessionService);
        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        Card card = new Card();
        bill1.setBalance(1000);
        bill2.setBalance(1000);
        bill1.setCurrency("USD");
        bill2.setCurrency("EUR");
        bill1.setCards(List.of(card));
        bill2.setCards(List.of(card));
        card.setBillList(List.of(bill1, bill2));
        BDDMockito.given(sessionService.getInitializeCard()).willReturn(card);
    }


    @Test
    void depositCash() {
        billService.depositCash(1000, "USD");
        Assertions.assertEquals(billService.getBillByCurrency("USD").getBalance(), 2000);
    }

    @Test
    void hasNeededCash() {
        Assertions.assertFalse(billService.hasNeededCash(2000, "USD"));
        Assertions.assertTrue(billService.hasNeededCash(1000, "USD"));
    }

    @Test
    void withdrawCash() {
        billService.withdrawCash(500, "EUR");
        Assertions.assertEquals(billService.getBillByCurrency("EUR").getBalance(), 500);
    }

    @Test
    void getBillByCurrency() {
        Bill testBill = billService.getBillByCurrency("USD");
        Assertions.assertEquals(testBill.getCurrency(), "USD");
        Bill testNullBill = billService.getBillByCurrency("RUB");
        Assertions.assertNull(testNullBill);
    }
}