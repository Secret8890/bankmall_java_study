package com.template.api.apps.loan.dto;

import com.google.common.collect.Lists;
import com.template.api.apps.loan.domain.Loan;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Setter
@Getter
public class LoanDto {
        @ApiModelProperty(value = "금융권")
        private String financeType;

        @ApiModelProperty(value = "은행명")
        private String bankName;


        @ApiModelProperty(value = "대출상품")
        private String loanType;

        @ApiModelProperty(value = "기준금리")
        private Double baseRate;

        @ApiModelProperty(value = "가산금리")
        private Double addRate;

        @ApiModelProperty(value = "할증금리")
        private String rateBySpecial;

////        @ApiModelProperty(value = "할인목록들")
////        private List<SalesDto> salesList = Lists.newArrayList();

        @ApiModelProperty(value = "대출금리")
        private Double rate;

        @ApiModelProperty(value = "상환기간")
        private Long returnYear;

        @ApiModelProperty(value = "최소상환기간")
        private Long minReturnYear;

        @ApiModelProperty(value = "최대상환기간")
        private Long maxReturnYear;

        @ApiModelProperty(value = "중도상환수수료")
        private Double repaymentFees;

        @ApiModelProperty(value = "상환방식")
        private String returnMethod;

        @ApiModelProperty(value = "중도상환수수료 면제율")
        private Double repaymentFeesYear;

        public Loan toEntity() {
                return new Loan(null, financeType, bankName, loanType, baseRate, addRate, rateBySpecial, rate, returnYear, minReturnYear, maxReturnYear, repaymentFees, returnMethod, repaymentFeesYear);
        }



//    public static class SalesDto {
//        private Long id;
//
//        private String rateBySpecial; // 할인항목
//
//        private Double disCountRows; // 할인금리
//    }
}
