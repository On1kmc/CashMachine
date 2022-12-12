package com.example.CashMashine.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "manipulator")
public class Manipulator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String currency;

    @OneToMany(mappedBy = "manipulator")
    private List<Banknote> banknotes;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currencyCode) {
        this.currency = currencyCode;
    }

    public List<Banknote> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(List<Banknote> banknotes) {
        this.banknotes = banknotes;
    }

    public int getId() {
        return id;
    }
}
