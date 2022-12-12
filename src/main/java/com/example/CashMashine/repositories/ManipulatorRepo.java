package com.example.CashMashine.repositories;

import com.example.CashMashine.models.Manipulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManipulatorRepo extends JpaRepository<Manipulator, Integer> {


}
