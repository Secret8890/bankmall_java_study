package com.template.api.apps.loan.domain;

import com.google.common.collect.Lists;
import com.template.api.apps.loan.dto.LoanDto;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Loan {

    @Id // 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 생성 어노테이션
    private Long id;
    private String financeType;
    private String bankName;
    private String loanType;
    private Double baseRate;
    private Double addRate;
    private String rateBySpecial;

////        private List<SalesDto> salesList = Lists.newArrayList();
    private Double rate;
    private Long returnYear;
    private Long minReturnYear;
    private Long maxReturnYear;
    private Double repaymentFees;
    private String returnMethod;
    private Double repaymentFeesYear;

    public void update(Loan loan) {
        this.financeType = loan.financeType;
        this.bankName = loan.bankName;
        this.loanType = loan.loanType;
        this.baseRate = loan.baseRate;
        this.addRate = loan.addRate;
        this.rateBySpecial = loan.rateBySpecial;
        this.rate = loan.rate;
        this.returnYear = loan.returnYear;
        this.minReturnYear = loan.minReturnYear;
        this.maxReturnYear = loan.maxReturnYear;
        this.repaymentFees = loan.repaymentFees;
        this.returnMethod = loan.returnMethod;
        this.repaymentFeesYear = loan.repaymentFeesYear;
    }

}
