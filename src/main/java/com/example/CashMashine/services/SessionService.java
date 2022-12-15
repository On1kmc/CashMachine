package com.example.CashMashine.services;

import com.example.CashMashine.models.Bill;
import com.example.CashMashine.models.Card;
import com.example.CashMashine.repositories.CardRepo;
import lombok.Data;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
public class SessionService {

    private final CardRepo cardRepo;

    private Card card;

    public SessionService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Transactional
    public Card getInitializeCard() {
        Card initCard = cardRepo.findById(card.getId()).orElse(null);
        assert initCard != null;
        List<Bill> billList = initCard.getBillList();
        Hibernate.initialize(billList);
        return initCard;
    }
}
