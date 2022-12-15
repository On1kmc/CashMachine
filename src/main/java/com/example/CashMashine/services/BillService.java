package com.example.CashMashine.services;

import com.example.CashMashine.models.Bill;
import com.example.CashMashine.repositories.BillRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class BillService {

    private final BillRepo billRepo;
    private final SessionService sessionService;


    @Transactional
    public void depositCash(int cashForDeposit, String currency) {
        Bill bill = getBillByCurrency(currency);
        bill.setBalance(bill.getBalance() + cashForDeposit);
        billRepo.save(bill);
    }

    public boolean hasNeededCash(int expectedCash, String currency) {
        Bill bill = getBillByCurrency(currency);
        return bill.getBalance() >= expectedCash;
    }


    @Transactional
    public void withdrawCash(int cashForWithdraw, String currency) {
        Bill bill = getBillByCurrency(currency);
        bill.setBalance(bill.getBalance() - cashForWithdraw);
        billRepo.save(bill);
    }


    public Bill getBillByCurrency(String currency) {
        List<Bill> billList = sessionService.getInitializeCard().getBillList();
        for (Bill bill : billList) {
            if (bill.getCurrency().equalsIgnoreCase(currency)) {
                return bill;
            }
        }
        return null;
    }
}
