package com.example.CashMashine.repositories;

import com.example.CashMashine.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepo extends JpaRepository<Bill, Integer> {

}
