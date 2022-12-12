package com.example.CashMashine.repositories;

import com.example.CashMashine.CurrencyManipulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManipulatorRepo extends JpaRepository<CurrencyManipulator, Integer> {


}
