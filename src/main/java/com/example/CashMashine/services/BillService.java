package com.example.CashMashine.services;

import com.example.CashMashine.models.Bill;
import com.example.CashMashine.repositories.BillRepo;
import com.example.CashMashine.repositories.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BillService {

    private final BillRepo billRepo;
    private final SessionService sessionService;

    @Autowired
    public BillService(BillRepo billRepo, SessionService sessionService) {
        this.billRepo = billRepo;
        this.sessionService = sessionService;
    }


    @Transactional
    public void depositCash(int cashForDeposit, String currency) {
        List<Bill> billList = sessionService.getInitializeCard().getBillList();
        for (Bill bill : billList) {
            if (bill.getCurrency().equalsIgnoreCase(currency)) {
                bill.setBalance(bill.getBalance() + cashForDeposit);
                billRepo.save(bill);
            }
        }
    }
}
