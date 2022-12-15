package com.example.CashMashine.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "manipulator")
@Data
public class Manipulator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String currency;

    @OneToMany(mappedBy = "manipulator", fetch = FetchType.EAGER)
    private List<Banknote> banknotes;

    public String getCurrency() {
        return currency;
    }


    public List<Banknote> getBanknotes() {
        return banknotes;
    }

}
