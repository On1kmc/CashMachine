package com.example.CashMashine.repositories;

import com.example.CashMashine.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo  extends JpaRepository<Card, Integer> {

    Card findCardByCardNumber(long cardNumber);
}
