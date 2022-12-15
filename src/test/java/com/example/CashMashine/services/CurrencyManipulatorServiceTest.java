package com.example.CashMashine.services;

import com.example.CashMashine.exception.NotEnoughMoneyException;
import com.example.CashMashine.models.Banknote;
import com.example.CashMashine.models.Manipulator;
import com.example.CashMashine.repositories.BanknoteRepo;
import com.example.CashMashine.repositories.ManipulatorRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
class CurrencyManipulatorServiceTest {


    @MockBean
    private ManipulatorRepo manipulatorRepo;

    @MockBean
    private BanknoteRepo banknoteRepo;

    public CurrencyManipulatorService currencyManipulatorService;


    @BeforeEach
    public void initialize() {
        currencyManipulatorService = new CurrencyManipulatorService(manipulatorRepo, banknoteRepo);
        Banknote banknote1 = new Banknote();
        banknote1.setCurrency("RUB");
        banknote1.setNominal(500);
        banknote1.setCount(1);
        Banknote banknote2 = new Banknote();
        banknote2.setCurrency("RUB");
        banknote2.setNominal(100);
        banknote2.setCount(10);
        Manipulator manipulator2 = new Manipulator();
        manipulator2.setCurrency("RUB");
        Manipulator manipulator1 = new Manipulator();
        manipulator1.setCurrency("EUR");
        banknote1.setManipulator(manipulator2);
        banknote2.setManipulator(manipulator2);
        manipulator2.setBanknotes(List.of(banknote1, banknote2));
        BDDMockito.given(banknoteRepo.findAllByCurrencyAndNominal("RUB", 500)).willReturn(banknote1);
        BDDMockito.given(banknoteRepo.findAllByCurrencyAndNominal("RUB", 100)).willReturn(banknote2);
        BDDMockito.given(manipulatorRepo.findByCurrency("RUB")).willReturn(manipulator2);
        BDDMockito.given(banknoteRepo.findAllByCurrency("RUB")).willReturn(List.of(banknote1, banknote2));
        BDDMockito.given(manipulatorRepo.findAll()).willReturn(List.of(manipulator1, manipulator2));
    }

    @Test
    void addAmount() {
        currencyManipulatorService.addAmount("RUB", 500, 1);
        Assertions.assertEquals(banknoteRepo.findAllByCurrencyAndNominal("RUB", 500).getCount(), 2);
        currencyManipulatorService.addAmount("RUB", 200, 1);
    }

    @Test
    void getTotalAmount() {
        Assertions.assertEquals(1500, currencyManipulatorService.getTotalAmount("RUB"));
    }

    @Test
    void hasManipulator() {
        Assertions.assertTrue(currencyManipulatorService.hasManipulator("RUB"));
        Assertions.assertFalse(currencyManipulatorService.hasManipulator("USD"));
    }

    @Test
    void getAllManipulators() {
        Assertions.assertEquals(2, currencyManipulatorService.getAllManipulators().size());
    }

    @Test
    void withdrawAmount() throws NotEnoughMoneyException {
        Assertions.assertThrows(NotEnoughMoneyException.class, () -> {
            currencyManipulatorService.withdrawAmount(2000, "RUB");
        });
        Map<Integer, Integer> withdrawnMap = currencyManipulatorService.withdrawAmount(1000, "RUB");
        Assertions.assertEquals(1, withdrawnMap.get(500));
        Assertions.assertEquals(5, withdrawnMap.get(100));
    }


}