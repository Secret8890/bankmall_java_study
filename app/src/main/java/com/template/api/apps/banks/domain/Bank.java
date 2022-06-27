package com.template.api.apps.banks.domain;

import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Banks")

public class Bank extends BaseDomainWithId implements DomainWithMapper<BankDto.Response> {


    private String financeType; // 금융사 종류

    private String bankName; // 금융사 명

    private String loanType; // 금리형태

    private Double baseRate; // 기준금리

    private Double addRate; // 가산금리

//    private String upfinanceType; // update 금융사 종류
//
//    private String upbankName; // update  금융사 명
//
//    private String uploanType; // update  금리형태
//
//    private Double upbaseRate; // update  기준금리
//
//    private Double upaddRate; // update  가산금리

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sales> discountRows = new ArrayList<Sales>();

    private Double rate; //  최종금리

    private Long returnYear; // 상환기간

    private Long minReturnYear; //  최소기간

    private Long maxReturnYear; //  최대기간

    private Double repaymentFees; //  중도상환수수료율

    private String returnMethod; //  상환방식

    private String repaymentFeesYear; //  몇년간

    private Double uprate; // update 최종금리

    private Long upreturnYear; // update 상환기간

    private Long upminReturnYear; // update 최소기간

    private Long upmaxReturnYear; // update 최대기간

    private Double uprepaymentFees; // update 중도상환수수료율

    private String upreturnMethod; // update 상환방식

    private String uprepaymentFeesYear; // update 몇년간
    //private  bLogo
    //private  sLogo
    @Override
    public BankDto.Response toResponse() throws NullPointerException {
        return BankDtoMapper.INSTANCE.toResponse(this);
    }

//    public Bank update (Bank bank){
//        this.repaymentFees = new Bank().getRepaymentFees();
//        this.financeType = new Bank().getFinanceType();
//        this.bankName = new Bank().getBankName();
//        this.loanType = new Bank().getLoanType();
//        this.baseRate = new Bank().getBaseRate();
//        this.addRate = new Bank().getAddRate();
//        this.returnYear = new Bank().getReturnYear();
//        this.minReturnYear = new Bank().getMinReturnYear();
//        this.maxReturnYear = new Bank().getMaxReturnYear();
//        this.repaymentFees = new Bank().getRepaymentFees();
//        this.returnMethod = new Bank().getReturnMethod();
//        this.repaymentFeesYear = new Bank().getRepaymentFeesYear();
//
//        return this;
//    }
//    @Builder
//
//    public Bank banklist (String financeType, String bankName, String loanType, Double baseRate,
//                          Double addRate, Double rate, Long returnYear,
//                          Long minReturnYear, Long maxReturnYear, Double repaymentFees,
//                          String returnMethod, String repaymentFeesYear){
//        this.financeType = financeType;
//        this.bankName = bankName;
//        this.loanType = loanType;
//        this.baseRate = baseRate;
//        this.addRate = addRate;
//        this.rate = rate;
//        this.returnYear = returnYear;
//        this.minReturnYear = minReturnYear;
//        this.maxReturnYear = maxReturnYear;
//        this.repaymentFees = repaymentFees;
//        this.returnMethod = returnMethod;
//        this.repaymentFeesYear = repaymentFeesYear;
//
//        return Bank.this;
//    }
//    public BankBuilder newbanklist(){
//
//        return new BankBuilder()
//                .bankName(this.bankName)
//                .financeType(this.financeType)
//                .loanType(this.loanType)
//                .baseRate(this.baseRate)
//                .addRate(this.addRate)
//                .rate(this.rate)
//                .returnYear(this.returnYear)
//                .minReturnYear(this.minReturnYear)
//                .maxReturnYear(this.maxReturnYear)
//                .repaymentFees(this.repaymentFees)
//                .returnMethod(this.returnMethod)
//                .repaymentFeesYear(this.repaymentFeesYear);
//    }

}


