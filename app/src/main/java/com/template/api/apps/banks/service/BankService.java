package com.template.api.apps.banks.service;

import com.google.common.collect.Lists;
import com.template.api.apps.banks.domain.Bank;
import com.template.api.apps.banks.domain.BankRepository;
import com.template.api.apps.banks.domain.Sales;
import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
@Primary

public class BankService{
    private final BankRepository bankRepository;

    // 금융사 내용 수정
    @Transactional
    public void update(Long id, BankDto.Update update) throws NullPointerException {

        Bank bank = bankRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 입니다."));

        BankDtoMapper.INSTANCE.update(update,bank);

        for(BankDto.SaleUpdate item : update.getDiscountrows() ){

            Sales sales = BankDtoMapper.INSTANCE.update(item);
            sales.setBank(bank);
            bank.getDiscountrows().add(sales);
        }

        List<Sales> discountrows = Lists.newArrayList();

        bank.getDiscountrows().addAll(discountrows);

    }
    @Transactional
    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }

    // 금융사 등록
    @Transactional
    public void createBank (BankDto.Create create) throws NullPointerException{
        if (create == null) {
            throw new NullPointerException("정확한값을 입력해주세요 ..") ;
        } else {

//            create.setRate(create.getBaseRate()+ create.getAddRate()-create.getDiscountRate());

            Bank bank = BankDtoMapper.INSTANCE.create(create);
//            bank.getDiscountRows().clear(); //

            for(BankDto.SaleCreate item : create.getDiscountrows()) {

                Sales sales = BankDtoMapper.INSTANCE.createSales(item);

                bank.getDiscountrows().add(sales);

                sales.setBank(bank);

                bank.setRate((bank.getAddRate()+ bank.getBaseRate()- item.getDiscountRate()));
            }

            List<Sales> discountrows = Lists.newArrayList();

            bank.getDiscountrows().addAll(discountrows);

            bankRepository.save(bank);
        }
    }
//    @Transactional
//    public void Update(Long id ,BankDto.Update update) throws NullPointerException{
//        if(update == null){
//            throw new NullPointerException("값을 정확히 입력하세요 ");
//        } else {
//            BankDto.Update bank = BankDtoMapper.INSTANCE.Update(update);
//        }
//    }
//    @Transactional
//    public Bank calResponce (BankDto.Request request) throws NullPointerException{
//
//        Bank bank = BankDtoMapper.INSTANCE.CalResponce(request);
//        Bank bank = BankDtoMapper.INSTANCE.CalResponce(request);
//        Sales sales = new Sales();
//
//        bank.setRate(bank.getBaseRate()+bank.getAddRate()-sales.getDiscountRate());
//
//        return createBank(request);
//    }
//@Transactional
//public Bank calRate(BankDto.Request request){
//        Bank bank = BankDtoMapper.INSTANCE.calRate(request);
//        return calRate(request);
//}

//    @Transactional
//    public BankDto.Response calRate(BankDto.CalResponce responce) {
//
//        Bank bank = new Bank();
//        BankDtoMapper.INSTANCE.toResponse(bank);
//        Sales sales = new Sales();
//
//        bank.setRate(bank.getBaseRate() + bank.getAddRate() - sales.getDiscountRate());
//
//        return calRate(responce);
//    }

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

    @Transactional
    public PagableDto.Response<BankDto.SaleResponse> getPageLists(BankDto.Request request) {


        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())) {
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);

        Page<Bank> items = bankRepository.findAll(r.output(), pageRequest); //Page DATA GET

        PagableDto.Response<BankDto.SaleResponse> pages = PagableDto.Response.of(items); //Page response mapping

        //endregion
        return pages;
    }


    // 상세조회
    public BankDto.Response detail (Long id){
        Bank bank = bankRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 값입니다."));
        BankDto.Response response = bank.toResponse();
        if(bank.getDiscountrows() != null && bank.getDiscountrows().size()> 0 ) {
            response.setDiscountrows(bank.getDiscountrows().stream().map(Sales::toResponse).collect(Collectors.toList()));
        }

        return response;

    }
}
