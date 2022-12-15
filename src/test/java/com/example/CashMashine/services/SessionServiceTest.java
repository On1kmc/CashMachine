package com.example.CashMashine.services;

import com.example.CashMashine.models.Bill;
import com.example.CashMashine.models.Card;
import com.example.CashMashine.repositories.CardRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class SessionServiceTest {

    private SessionService sessionService;

    @MockBean
    private CardRepo cardRepo;

    private Card card;

    @Test
    void getInitializeCard() {
        sessionService = new SessionService(cardRepo);
        Card card = new Card();
        card.setCardNumber(123456789012L);
        sessionService.setCard(card);
        BDDMockito.given(cardRepo.findCardByCardNumber(BDDMockito.anyLong())).willReturn(card);
        BDDMockito.given(cardRepo.findById(BDDMockito.any())).willReturn(Optional.of(card));
        List<Bill> billList = sessionService.getInitializeCard().getBillList();
    }
}