package com.template.api.apps.banks.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Banks")
@Getter
@Setter
public class Sales extends Bank  {

    private String rateBySpeical; // 할인항목

    private Double rateByUseMethodMax; // 할인금리

    @ManyToOne
    private Bank bank;

    public void Sales(String rateBySpeical, Double rateByUseMethodMax){
        this.rateBySpeical = rateBySpeical;
        this.rateByUseMethodMax = rateByUseMethodMax;
    }
//    @Override
//
//    public List<Sales> getSalesList() {
//        return setSalesList();
//    }
}