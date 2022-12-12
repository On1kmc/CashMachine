package com.example.CashMashine.models;

import jakarta.persistence.*;

@Entity
@Table(name = "banknote")
public class Banknote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String currency;

    private int nominal;

    private int count;

    @ManyToOne
    @JoinColumn(name = "manipulator_currency", referencedColumnName = "currency")
    private Manipulator manipulator;


    public int getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Manipulator getManipulator() {
        return manipulator;
    }

    public void setManipulator(Manipulator manipulator) {
        this.manipulator = manipulator;
    }
}
