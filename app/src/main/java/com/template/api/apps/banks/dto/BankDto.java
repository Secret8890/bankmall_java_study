package com.template.api.apps.banks.dto;

import com.google.common.collect.Lists;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.util.List;


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
    public static class Response{
        @ApiModelProperty(value = "고유번호")
        private Long id;

        @ApiModelProperty(value = "금융권")
        private String financeType;

        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @ApiModelProperty(value = "가산금리")
        private Double addRate;

//        @ApiModelProperty(value = "할인항목")
//        private String discountCondition;

        @ApiModelProperty(value = "대출금리")
        private Double rate;

        @ApiModelProperty(value = "기준금리")
        private Double baseRate;

//        @ApiModelProperty(value = "할인금리")
//        private Double discountRate;

        @ApiModelProperty(value = "대출기간")
        private Long returnYear;


        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;


        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;


        @ApiModelProperty(value = "중도상환수수료율")
        private Double repaymentFees;


        @ApiModelProperty(value = " 중도상환수수료 몇년간 ")
        private String repaymentFeesYear;


        @ApiModelProperty(value = " 중도상환수수료 면제 ")
        private String repaymentExemption;


        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;


        @ApiModelProperty(value = "대출금액")
        private Long loanMoney;


        @ApiModelProperty(value = "상환금액")
        private Long returnMoney;


        @ApiModelProperty(value = "할인금리리스트")
        private List<SaleResponse> discountrows = Lists.newArrayList();

    }
    @Getter
    @Setter
    @ApiModel("BankDto_Create")
    public static class Create  {

        @ApiModelProperty(value = "금융권")
        private String financeType;

        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @ApiModelProperty(value = "가산금리")
        private Double addRate;

        @ApiModelProperty(value = "대출금리")
        private Double rate;

        @ApiModelProperty(value = "기준금리")
        private Double baseRate;

        @ApiModelProperty(value = "대출기간")
        private Long returnYear;

        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;

        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;

        @ApiModelProperty(value = "중도상환수수료율")
        private Double repaymentFees;

        @ApiModelProperty(value = " 중도상환수수료 몇년간 ")
        private String repaymentFeesYear;

        @ApiModelProperty(value = " 중도상환수수료 면제 ")
        private String repaymentExemption;

        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;

        @ApiModelProperty(value = " 할인 ")
        private Double discountrate;

        @ApiModelProperty(value = "할인금리리스트")
        private List<SaleCreate> discountRows = Lists.newArrayList();

    }

    @Getter
    @Setter
    @ApiModel("BankDto_Update")
    public static class Update {
        @ApiModelProperty(value = "금융권")
        private String financeType;

        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @ApiModelProperty(value = "가산금리")
        private Double addRate;

        @ApiModelProperty(value = "대출금리")
        private Double rate;

        @ApiModelProperty(value = "기준금리")
        private Double baseRate;

        @ApiModelProperty(value = "할인금리리스트")
        private List<SaleUpdate> discountRows = Lists.newArrayList();

        @ApiModelProperty(value = "대출기간")
        private Long returnYear;


        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;


        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;


        @ApiModelProperty(value = "중도상환수수료율")
        private Double repaymentFees;

        @ApiModelProperty(value = " 중도상환수수료 몇년간 ")
        private String repaymentFeesYear;


        @ApiModelProperty(value = " 중도상환수수료 면제 ")
        private String repaymentExemption;


//        @ApiModelProperty(value = " 상환방식 ")
//        private String returnMethod;

    }


    @Getter
    @Setter
    @ApiModel("SaleDto")
    @Table(name = "discountRows")
    public static class SaleCreate {

        @ApiModelProperty(value = "할인항목")
        private String discountCondition;

        @ApiModelProperty(value = "할인금리")
        private Double discountRate;

    }
    @Getter
    @Setter
    @ApiModel("BankDto_UpdateSale")

    public static class SaleUpdate extends SaleResponse {

        @ApiModelProperty(value = "할인항목")
        private String discountCondition;

        @ApiModelProperty(value = "할인금리")
        private Double discountRate;
    }
    @Getter
    @Setter
    @ApiModel("BankDto_Detail")
    public static class Detail{

        @ApiModelProperty(value = "금융권")
        private String financeType;

        @ApiModelProperty(value = "금융사명")
        private String bankName;

        @ApiModelProperty(value = "금리형태 ")
        private String loanType;

        @ApiModelProperty(value = "가산금리")
        private Double addRate;

        @ApiModelProperty(value = "대출금리")
        private Double rate;

        @ApiModelProperty(value = "기준금리")
        private Double baseRate;

        @ApiModelProperty(value = "대출기간")
        private Long returnYear;

        @ApiModelProperty(value = " 최소대출기간 ")
        private Long minReturnYear;

        @ApiModelProperty(value = "최대대출기간")
        private Long maxReturnYear;

        @ApiModelProperty(value = "중도상환수수료율")
        private Double repaymentFees;

        @ApiModelProperty(value = " 중도상환수수료 몇년간 ")
        private String repaymentFeesYear;

        @ApiModelProperty(value = " 중도상환수수료 면제 ")
        private String repaymentExemption;

        @ApiModelProperty(value = " 상환방식 ")
        private String returnMethod;

        @ApiModelProperty(value = "할인금리리스트")
        private List<SaleCreate> discountRows = Lists.newArrayList();

    }
    @Getter
    @Setter
    @ApiModel("SaleListDto")
    public static class SaleResponse {
        @ApiModelProperty(value = "할인항목")
        private String discountCondition;

        @ApiModelProperty(value = "할인금리")
        private Double discountRate;
        }

        @Getter
        @Setter
        @ApiModel("BankDeleteDto")
        public static class delete {
            @ApiModelProperty(value = "금융사 삭제")
            private Long id;
        }
    }

