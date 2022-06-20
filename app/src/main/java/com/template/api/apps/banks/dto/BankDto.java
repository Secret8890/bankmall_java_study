package com.template.api.apps.banks.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
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
    public static class Response {

        @ApiModelProperty(value = "금융사관리번호")
        private Long id;

        @ApiModelProperty(value = "금융권")
        private String financeType;

        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @ApiModelProperty(value = "가산금리")
        private double addRate;

        @ApiModelProperty(value = "할인항목")
        private String rateBySpeical;

        @ApiModelProperty(value = "대출금리")
        private double rate;

        @ApiModelProperty(value = "기준금리")
        private double baseRate;

        @ApiModelProperty(value = "할인금리")
        private double rateByUseMethodMax;

        @ApiModelProperty(value = "대출기간")
        private Long returnYear;

        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;

        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;

        @ApiModelProperty(value = "중도상환수수료율")
        private double repaymentFeerate;

        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;


    }
    @Getter
    @Setter
    @ApiModel("BankDto_Create")
    public static class Create {

        @ApiModelProperty(value = "금융사관리번호")
        private Long id;

        @ApiModelProperty(value = "금융권")
        private String financeType;

        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @ApiModelProperty(value = "가산금리")
        private double addRate;

        @ApiModelProperty(value = "할인항목")
        private String rateBySpeical;

        @ApiModelProperty(value = "대출금리")
        private double rate;

        @ApiModelProperty(value = "기준금리")
        private double baseRate;

        @ApiModelProperty(value = "할인금리")
        private double rateByUseMethodMax;

        @ApiModelProperty(value = "대출기간")
        private Long returnYear;

        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;

        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;

        @ApiModelProperty(value = "중도상환수수료율")
        private double repaymentFeerate;

        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;


    }

}
