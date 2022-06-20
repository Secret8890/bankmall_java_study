package com.template.api.apps.bank.domain;


import com.template.api.apps.bank.dto.BankDto;
import com.template.api.apps.bank.dto.BankDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Bank extends BaseDomainWithId {

    private String bankId;
    private String bankName;
    private String financeType;
    private String loanType;
    private Double baseRate;
    private Double addRate;
    private String rateBySpeical;
    private Double rateByUseMethodMax;
    private Double rate;
    private Long returnYear;
    private Long minReturnYear;
    private Long maxReturnYear;
    private String repaymentFees;
    private String repaymentApplyyear;
    private String repaymentExemption;
    private String returnMethod;
    private String bLogo;
    private String sLogo;


    public BankDto.Response toResponse(){return BankDtoMapper.INSTANCE.toResponse(this);
    }
}
