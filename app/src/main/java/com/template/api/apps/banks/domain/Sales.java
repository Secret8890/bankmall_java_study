//package com.template.api.apps.banks.domain;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Table(name = "Banks")
//
//public class Sales extends Bank{
//
//    private String rateBySpeical; // 할인항목
//
//    private Double rateByUseMethodMax; // 할인금리
//
//    @ManyToOne
//    private Bank bank;
//
//    public Bank getBank() {
//        return bank;
//    }
//
//    public void setBank(Bank bank) {
//        this.bank = bank;
//    }
//
//
//}