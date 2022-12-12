package com.example.CashMashine.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "manipulator")
public class Manipulator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String currencyCode;

    @OneToMany(mappedBy = "manipulator")
    private List<Banknote> banknotes;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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
