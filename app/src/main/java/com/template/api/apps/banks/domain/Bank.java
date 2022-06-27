package com.template.api.apps.banks.domain;

import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String upfinanceType; // update 금융사 종류

    private String upbankName; // update  금융사 명

    private String uploanType; // update  금리형태

    private Double upbaseRate; // update  기준금리

    private Double upaddRate; // update  가산금리

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sales> salesList = new ArrayList<Sales>();

    private Double rate; // update 최종금리

    private Long returnYear; // update 상환기간

    private Long minReturnYear; // update 최소기간

    private Long maxReturnYear; // update 최대기간

    private Double repaymentFees; // update 중도상환수수료율

    private String returnMethod; // update 상환방식

    private String repaymentFeesYear; // update 몇년간

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
    public BankDto.Response toResponse() {
        return BankDtoMapper.INSTANCE.toResponse(this);
    }

}


