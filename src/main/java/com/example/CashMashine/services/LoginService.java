package com.example.CashMashine.services;

import com.example.CashMashine.models.Bill;
import com.example.CashMashine.repositories.BillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final BillRepo billRepo;

    @Autowired
    public LoginService(BillRepo billRepo) {
        this.billRepo = billRepo;
    }


    public List<Bill> findAllBillByCardNumber(long cardNumber) {
        return billRepo.findAllByCardNumber(cardNumber);
    }
}
