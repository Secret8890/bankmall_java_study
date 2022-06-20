package com.template.api.apps.banks.domain;

import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity

public class Bank extends BaseDomainWithId implements DomainWithMapper<BankDto.Response> {

    @Column(nullable = false, unique = true)

    private String id;

    private String financeType;

    private String bankName;

    private String loanType;

    private Double baseRate;

    private Double addRate;

    private String rateBySpeical;

    private Double rateByUseMethodMax;

    private Double rate;

    private Long returnYear;

    private Long minReturnYear;

    private Long maxReturnYear;

    private Double repaymentfeerate;

    private String returnMethod;



    //private  bLogo
    //private  sLogo
    @Override
    public BankDto.Response toResponse() {

        return BankDtoMapper.INSTANCE.toResponse(this);
    }
}


