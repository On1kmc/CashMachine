package com.example.CashMashine.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long cardNumber;

    private int pinCode;

    @ManyToMany
    @JoinTable(name = "card_bill",
                joinColumns = @JoinColumn(name = "card_id"),
                inverseJoinColumns = @JoinColumn(name = "bill_id"))
    private List<Bill> billList;

    public int getId() {
        return id;
    }


    public int getPinCode() {
        return pinCode;
    }

    public List<Bill> getBillList() {
        return billList;
    }

}
