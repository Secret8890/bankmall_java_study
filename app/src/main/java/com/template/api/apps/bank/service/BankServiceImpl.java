package com.template.api.apps.bank.service;


import com.amazonaws.services.kms.model.NotFoundException;
import com.google.common.collect.Lists;
import com.template.api.apps.bank.domain.Bank;
import com.template.api.apps.bank.domain.BankRepository;
import com.template.api.apps.bank.domain.Sales;
import com.template.api.apps.bank.dto.BankDto;
import com.template.api.apps.bank.dto.BankDtoMapper;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Qualifier("BankServiceImpl")
@Primary

public class BankServiceImpl {

    private final BankRepository bankRepository;


    // todo: 금융사명 조회
    public PagableDto.Response<BankDto.Response> getBanks(BankDto.Request request){

        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())) {
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }
        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);

        Page<Bank> items = bankRepository.findAll(r.output(), pageRequest);

        PagableDto.Response<BankDto.Response> pages = PagableDto.Response.of(items); //Page response mapping

        return pages;
    }
 // todo: 부수거래
    @Transactional
    public PagableDto.Response<BankDto.SaleResponse> getPageSales(BankDto.Request request){

        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())){
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);
        Page<Bank> items = bankRepository.findAll(r.output(), pageRequest);
        PagableDto.Response<BankDto.SaleResponse> pages = PagableDto.Response.of(items);

        return pages;
    }

    // todo: 금융사 등록
    @Transactional
    public void createBankName(BankDto.Create create) throws NotFoundException{
        if (create == null){
            throw new NotFoundException("동일한 금융사가 있습니다.");
        } else {

            Bank bank = BankDtoMapper.INSTANCE.create(create);

            for (BankDto.SaleCreate item : create.getDiscountRows()) {
                bank.setRate(bank.getAddRate() + bank.getBaseRate() - item.getDiscountRate());
            }

            List<Sales> discountRow = Lists.newArrayList();
            bank.getDiscountRow().addAll(discountRow);
            bankRepository.save(bank);
        }
    }

    // todo: 금융사 삭제
    @Transactional
    public void deleteBank(Long id){
        bankRepository.deleteById(id);
    }


    // todo: 금융사 수정
    @Transactional
    public void updateBank(Long id, BankDto.Update update) throws NotFoundException {
        // 404
        Bank bank = bankRepository.findById(id).orElseThrow(()->new NotFoundException("해당 ID의 은행은 존재하지않습니다."));
        BankDtoMapper.INSTANCE.update(update, bank);

        for (BankDto.SaleUpdate item : update.getDiscountRows()){

            Sales sales = BankDtoMapper.INSTANCE.update(item);
            sales.setBank(bank);
            bank.getDiscountRow().add(sales);
        }
        List<Sales> discountRows = Lists.newArrayList();
        bank.getDiscountRow().addAll(discountRows);


    }


}
