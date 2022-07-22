package com.template.api.apps.bank.domain;


import com.google.common.collect.Lists;
import com.template.api.apps.bank.dto.BankDto;
import com.template.api.apps.bank.dto.BankDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Banks")
public class Bank extends BaseDomainWithId implements DomainWithMapper
       //<BankDto.Response>
{

    private String bankId;
    private String bankName; //금융사명
    private String financeType;  // 금융사 종류
    private String loanType; // 금리형태
    private Double baseRate; // 기준금리
    private Double addRate; // 가산금리
    private Double rate; //대출금리
    private Long returnYear; //대출기간
    private Long minReturnYear; // 대출최소기간
    private Long maxReturnYear; // 대출최대기간
    private String repaymentFees; // 중도상환수수료
    private String repaymentApplyyear; // 매년, 3년간
    private String repaymentExemption; // 면제비율
    private String returnMethod; // 상환방식
    private String bLogo; // 큰 로고 이미지
    private String sLogo; // 작은 로고 이미지

    @OneToMany(mappedBy  ="bank", fetch = FetchType.LAZY)
    private List<Sales> discountRow = Lists.newArrayList(); // 부수거래 항목
//
//    private String discountCondition; // 부수거래 내용
//    private Double discountRate; // 부수거래 이자


    @Override
    public BankDto.Response toResponse(){return BankDtoMapper.INSTANCE.toResponse(this);
    }
}
