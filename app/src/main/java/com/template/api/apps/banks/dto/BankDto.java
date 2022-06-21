package com.template.api.apps.banks.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;


@Data
@Table(name = "Banks")

public class BankDto {
    @Getter
    @Setter
    @ApiModel("BankDto_Request")
    public static class Request extends PagableDto.Request {

        private String keyword;
    }

    @Getter
    @Setter
    @ApiModel("BankDto_Response")

    public static class Response{

        @Column (name = "financeType")
        @ApiModelProperty(value = "금융권")
        private String financeType;

        @Column (name = "bankName")
        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @Column (name = "loanType")
        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @Column (name = "addRate")
        @ApiModelProperty(value = "가산금리")
        private double addRate;

        @Column (name = "rateBySpeical")
        @ApiModelProperty(value = "할인항목")
        private String rateBySpeical;

        @Column (name = "rate")
        @ApiModelProperty(value = "대출금리")
        private double rate;

        @Column (name = "baseRate")
        @ApiModelProperty(value = "기준금리")
        private double baseRate;

        @Column (name = "rateByUseMethodMax")
        @ApiModelProperty(value = "할인금리")
        private double rateByUseMethodMax;

        @Column (name = "returnYear")
        @ApiModelProperty(value = "대출기간")
        private Long returnYear;

        @Column (name = "minReturnYear")
        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;

        @Column (name = "maxReturnYear")
        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;

        @Column (name = "repaymentFees")
        @ApiModelProperty(value = "중도상환수수료율")
        private double repaymentFees;

        @Column (name = "repaymentFeesYear")
        @ApiModelProperty(value = " 중도상환수수료 몇년간 ")
        private String repaymentFeesYear;

        @Column (name = "repaymentExemption")
        @ApiModelProperty(value = " 중도상환수수료 면제 ")
        private String repaymentExemption;

        @Column (name = "returnMethod")
        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;

        @Column (name = "loanMoney")
        @ApiModelProperty(value = "대출금액")
        private Long loanMoney;

        @Column (name = "returnMoney")
        @ApiModelProperty(value = "상환금액")
        private Long returnMoney;


    }
    @Getter
    @Setter
    @ApiModel("BankDto_Create")
    public static class Create  {

        @Column (name = "financeType")
        @ApiModelProperty(value = "금융권")
        private String financeType;


        @Column (name = "bankName")
        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @Column (name = "loanType")
        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @Column (name = "addRate")
        @ApiModelProperty(value = "가산금리")
        private double addRate;

        @Column (name = "rateBySpeical")
        @ApiModelProperty(value = "할인항목")
        private String rateBySpeical;

        @Column (name = "rate")
        @ApiModelProperty(value = "대출금리")
        private double rate;

        @Column (name = "baseRate")
        @ApiModelProperty(value = "기준금리")
        private double baseRate;

        @Column (name = "rateByUseMethodMax")
        @ApiModelProperty(value = "할인금리")
        private double rateByUseMethodMax;

        @Column (name = "returnYear")
        @ApiModelProperty(value = "대출기간")
        private Long returnYear;

        @Column (name = "minReturnYear")
        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;

        @Column (name = "maxReturnYear")
        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;

        @Column (name = "repaymentFees")
        @ApiModelProperty(value = "중도상환수수료율")
        private double repaymentFees;

        @Column (name = "repaymentFeesYear")
        @ApiModelProperty(value = " 중도상환수수료 몇년간 ")
        private String repaymentFeesYear;

        @Column (name = "repaymentExemption")
        @ApiModelProperty(value = " 중도상환수수료 면제 ")
        private String repaymentExemption;

        @Column (name = "returnMethod")
        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;
    }

    @Getter
    @Setter
    @ApiModel("BankDto_Update")
    public static class Update {

        @Column (name = "financeType")
        @ApiModelProperty(value = "금융권")
        private String financeType;

        @Column (name = "bankName")
        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @Column (name = "loanType")
        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @Column (name = "addRate")
        @ApiModelProperty(value = "가산금리")
        private double addRate;

        @Column (name = "rateBySpeical")
        @ApiModelProperty(value = "할인항목")
        private String rateBySpeical;

        @Column (name = "rate")
        @ApiModelProperty(value = "대출금리")
        private double rate;

        @Column (name = "baseRate")
        @ApiModelProperty(value = "기준금리")
        private double baseRate;

        @Column (name = "rateByUseMethodMax")
        @ApiModelProperty(value = "할인금리")
        private double rateByUseMethodMax;

        @Column (name = "returnYear")
        @ApiModelProperty(value = "대출기간")
        private Long returnYear;

        @Column (name = "minReturnYear")
        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;

        @Column (name = "maxReturnYear")
        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;

        @Column (name = "repaymentFees")
        @ApiModelProperty(value = "중도상환수수료율")
        private double repaymentFees;

        @Column (name = "repaymentFeesYear")
        @ApiModelProperty(value = " 중도상환수수료 몇년간 ")
        private String repaymentFeesYear;

        @Column (name = "repaymentExemption")
        @ApiModelProperty(value = " 중도상환수수료 면제 ")
        private String repaymentExemption;

        @Column (name = "returnMethod")
        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;

    }
}
