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
    @Column
    private String financeType;
    @Column
    private String bankName;
    @Column
    private String loanType;
    @Column
    private Double baseRate;
    @Column
    private Double addRate;
    @Column
    private String rateBySpecial;

////        private List<SalesDto> salesList = Lists.newArrayList();
    @Column
    private Double rate;
    @Column
    private Long returnYear;
    @Column
    private Long minReturnYear;
    @Column
    private Long maxReturnYear;
    @Column
    private Double repaymentFees;
    @Column
    private String returnMethod;
    @Column
    private Double repaymentFeesYear;



    public void put(Loan loan) {
        if (loan.bankName != null) {
            // 만약 loan에 있는 bankName 데이터가 null이 아니라면
            this.bankName = loan.bankName;
            // this.bankName은 loan.bankName과 같다
        }
    }
}
