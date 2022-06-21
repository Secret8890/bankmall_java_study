package com.template.api.apps.banks.domain;

import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Data
@Table(name = "Banks")

public class Bank extends BaseDomainWithId implements DomainWithMapper<BankDto.Response> {

    private String financeType; // 금융사 종류

    private String bankName; // 금융사 명

    private String loanType; // 금리형태

    private Double baseRate; // 기준금리

    private Double addRate; // 가산금리

//    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
//
//    private List<Sales> salesList = new java.util.ArrayList<>();

    private String rateBySpeical; // 할인항목

    private Double rateByUseMethodMax; // 할인금리

    private Double rate; // 최종금리

    private Long returnYear; // 상환기간

    private Long minReturnYear; // 최소기간

    private Long maxReturnYear; // 최대기간

    private Double repaymentFees; // 중도상환수수료율

    private String returnMethod; // 상환방식

    private String repaymentFeesYear; // 몇년간
    //private  bLogo
    //private  sLogo
    @Override
    public BankDto.Response toResponse() {
        return BankDtoMapper.INSTANCE.toResponse(this);
    }
}


