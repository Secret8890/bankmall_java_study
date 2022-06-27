package com.template.api.apps.banks.domain;

import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Sales")
@Getter
@Setter
public class Sales extends Bank  implements DomainWithMapper<BankDto.Response>  {

    private String discountCondition; // 할인항목

    private Double discountRate; // 할인금리

    @ManyToOne
    private Bank bank;
    @Override
    public BankDto.Response toResponse() {
        return BankDtoMapper.INSTANCE.toResponse(this);
    }
//    public void Sales(String discountCondition, Double discountRate){
//        this.discountCondition = discountCondition;
//        this.discountRate = discountRate;
//    }
//    @Override
//
//    public List<Sales> getSalesList() {
//        return setSalesList();
//    }
}