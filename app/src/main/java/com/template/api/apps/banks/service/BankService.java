package com.template.api.apps.banks.service;


import com.template.api.apps.banks.domain.Bank;
import com.template.api.apps.banks.domain.BankRepository;
import com.template.api.apps.banks.domain.Sales;
import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)

public class BankService {
    private final BankRepository bankRepository ;

    @Transactional
    public void createBank (BankDto.Create create) throws NullPointerException {
        if (create == null) {
            throw new NullPointerException("정확한값을 입력해주세요 ..");

//        if (create.getBankName()==null){
//        throw new NullPointerException();

//        else if(create.getBankName()==null){
//            throw new NullPointerException("금융사명을 입력해주세요");
//        } else if(create.getFinanceType()==null){
//            throw new NullPointerException("금융권을 체크해주세요");
//        } else if (create.getBaseRate()<0){
//            throw new NullPointerException("올바른 값을 입력해주세요");
        } else {
            //create.setRate(create.getBaseRate()+ create.getAddRate()-create.getRateByUseMethodMax());

            Bank bank = BankDtoMapper.INSTANCE.create(create);

            bankRepository.save(bank);
        }

    }
        @Transactional
        public void createList(BankDto.SaleCreate salecreate) throws NullPointerException {
            if (salecreate == null) {
                throw new NullPointerException("정확한값을 입력해주세요 ..");
            } else {
                Sales sales = BankDtoMapper.INSTANCE.create(salecreate);
                bankRepository.save(sales);
            }
        }

//        if (create.getBaseRate() >= 0){
//            create.setRate(create.getBaseRate()+ create.getAddRate()-create.getRateByUseMethodMax());
//
//            Bank bank = BankDtoMapper.INSTANCE.create(create);
//            bankRepository.save(bank);
//
//        }else {
//            throw new NullPointerException("값을 정확히 입력하세요...");
//        }

//    @Transactional
//    public void responseList(BankDto.SaleResponse saleresponse) throws NullPointerException {
//        if (saleresponse == null) {
//            throw new NullPointerException("값이 없습니다 ..");
//        } else {
//            List<BankDto.SaleResponse> sales = BankDtoMapper.INSTANCE.toResponse(saleresponse);
//        }

    @Transactional

    public void responseBank(BankDto.Response response){
        if(response.getBaseRate() >= 0 ){
            response.setRate(response.getBaseRate()+ response.getAddRate()-response.getRateByUseMethodMax());
            Bank bank = BankDtoMapper.INSTANCE.calRate(response);
        } else {
            throw new NullPointerException("값을 정확히 입력하세요");
        }
        if(response.getLoanMoney()>=0) {
            response.setReturnMoney((long) ((response.getLoanMoney() * response.getRate() / 12) + (response.getLoanMoney() / (response.getReturnYear()) * 12)));
        }else {
            throw new NullPointerException("값을 정확히 입력하세요...");
        }
    }

    @Transactional
    public BankDto.Update bankAccount(long id, BankDto.Update update) {

        return null;
    }

    @Transactional
    public void calRate(BankDto.Response response){
        if(response.getRate()<0){
            throw new NullPointerException("정확한 값을 입력해주세요");
        } else{
            Bank bank = BankDtoMapper.INSTANCE.calRate(response);

        }

    }


    @Transactional

    public PagableDto.Response<BankDto.Response> getPageBanks(BankDto.Request request) {


        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())) {
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);

        Page<Bank> items = bankRepository.findAll(r.output(), pageRequest); //Page DATA GET

        PagableDto.Response<BankDto.Response> pages = PagableDto.Response.of(items); //Page response mapping

        //endregion

        return pages;
    }

    public List<BankDto.Response> getBanks(BankDto.Request request) {
        return null;
    }

}
