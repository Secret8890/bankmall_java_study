package com.template.api.apps.bank.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class BankDto {

    @Getter
    @Setter
    @ApiModel("BankDto_Request")
    public static  class Request extends PagableDto.Request{
        @ApiModelProperty(value = "검색어", position = 2)
        private String keyword;
    }

    @Getter
    @Setter
    @ApiModel("BankDto_Create")
    public static class Create{
        @ApiModelProperty(value = "금융사 명")
        private String bankName;

        @ApiModelProperty(value =  "금융사 종류")
        private String financeType;

        @ApiModelProperty(value = " 금리 형태")
        private String loanType;

        @ApiModelProperty(value = "기준 금리")
        private Double baseRate;

        @ApiModelProperty(value = "가산 금리")
        private Double addRate;

        @ApiModelProperty(value = "할인 항목")
        private String rateBySpeical;

        @ApiModelProperty(value = "할인 금리")
        private Double rateByUseMethodMax;

        @ApiModelProperty(value = "대출 금리")
        private Double rate;

        @ApiModelProperty(value = "대출 기간")
        private Long returnYear;

        @ApiModelProperty(value = "최소 기간")
        private Long minReturnYear;

        @ApiModelProperty(value = "최대 기간")
        private Long maxReturnYear;

        @ApiModelProperty(value = "중도상환수수료")
        private Double repaymentFees;

        @ApiModelProperty(value = "중도상환수수료 몇 년간")
        private String repaymentApplyyear;

        @ApiModelProperty(value = "중도상환수수료 면제")
        private String repaymentExemption;

        @ApiModelProperty(value = "상환 방식")
        private String returnMethod;

        @ApiModelProperty(value = "큰로고 이미지")
        private String bLogo;

        @ApiModelProperty(value = "작은로고 이미지")
        private String sLogo;


    }
    @Getter
    @Setter
    @ApiModel("BankDto_Response")
    public static class Response{
        @ApiModelProperty("은행관리번호")
        private  Long id;

        @ApiModelProperty(value = "금융사명")
        private  String bankName;

        @ApiModelProperty(value = "금융사 종류")
        private String financeType;

        @ApiModelProperty(value = "금리 형태")
        private  String loanType;

        @ApiModelProperty(value = "기준 금리")
        private Double baseRate;

        @ApiModelProperty(value = "가산 금리")
        private Double addRate;

        @ApiModelProperty(value = "할인 항목목")
       private String rateBySpeical;

        @ApiModelProperty(value = "할인 금리")
        private Double rateByUseMethodMax;

        @ApiModelProperty(value = "대출 금리")
        private Double rate;

        @ApiModelProperty(value = "대출 기간")
        private Long returnYear;

        @ApiModelProperty(value = "최소 기간")
        private Long minReturnYear;

        @ApiModelProperty(value = "최대 기간")
        private Long maxReturnYear;

        @ApiModelProperty(value = "중도상환수수료")
        private Double repaymentFees;

        @ApiModelProperty(value = "중도상환수수료 몇 년간")
        private String repaymentApplyyear;

        @ApiModelProperty(value = "중도상환수수료 면제")
        private String repaymentExemption;

        @ApiModelProperty(value = "상환 방식")
        private String returnMethod;

        @ApiModelProperty(value = "큰로고 이미지")
        private String bLogo;

        @ApiModelProperty(value = "작은로고 이미지")
        private String sLogo;


    }


}
