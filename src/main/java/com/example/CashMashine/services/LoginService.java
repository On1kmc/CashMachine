package com.example.CashMashine.services;

import com.example.CashMashine.models.Card;
import com.example.CashMashine.repositories.BillRepo;
import com.example.CashMashine.repositories.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final CardRepo cardrepo;

    @Autowired
    public LoginService(CardRepo cardrepo) {
        this.cardrepo = cardrepo;
    }

    public Card findByCardNumber(long cardNumber) {
        return cardrepo.findCardByCardNumber(cardNumber);
    }
}
