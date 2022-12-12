package com.example.CashMashine.repositories;

import com.example.CashMashine.models.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BanknoteRepo extends JpaRepository<Banknote, Integer> {

    Banknote findAllByCurrencyAndNominal(String currency, int nominal);

    List<Banknote> findAllByCurrency(String currency);
}
