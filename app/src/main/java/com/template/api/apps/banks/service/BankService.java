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

public class BankService{
    private final BankRepository bankRepository;


    @Transactional
    public void update(Long id, BankDto.Update update) throws NullPointerException {

        Bank bank = bankRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 입니다."));

        BankDtoMapper.INSTANCE.update(update,bank);
//

        for(BankDto.SaleUpdate item : update.getDiscountRows() ){

            Sales sales = BankDtoMapper.INSTANCE.update(item);
            sales.setBank(bank);
            bank.getDiscountRows().add(sales);
        }

        List<Sales> discountRows = Lists.newArrayList();

        bank.getDiscountRows().addAll(discountRows);

    }



//    public Boolean update (Bank bank, long id) {
//        Bank before = bankRepository.findById(id).get();
//
//        if(before.getId()==(bank.update(bank).getId())){
//            before.update(bank.update(bank));
//            bankRepository.save(bank);
//            return true;
//        }
//        return false;
//    }



    @Transactional
    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }

//    @Transactional
//    public void Update (Long id) {
//        bankRepository.findById(id);
//    }
//    @Transactional
//    public List<BankDto> searchList(String keyword) {
//
//        return bankRepository.findAll(keyword).stream()
//                .map(BankDto::new)
//                .collect(Collectors.toList());
//    }
    @Transactional
    public void createBank (BankDto.Create create) throws NullPointerException{
        if (create == null) {
            throw new NullPointerException("정확한값을 입력해주세요 ..") ;
        } else {

            Bank bank = BankDtoMapper.INSTANCE.create(create);
//            bank.getDiscountRows().clear(); //

            for(BankDto.SaleCreate item : create.getDiscountRows()) {
                Sales sales = BankDtoMapper.INSTANCE.createSales(item);
                sales.setBank(bank);
                bank.getDiscountRows().add(sales);

            }
            List<Sales> discountRows = Lists.newArrayList();

            bank.getDiscountRows().addAll(discountRows);
            create.setRate(create.getBaseRate()+ create.getAddRate());
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
        @Transactional

        public void creatdiscountrows(BankDto.SaleCreate salecreate) throws NullPointerException {
            if (salecreate == null) {
                throw new NullPointerException("정확한값을 입력해주세요 ..");
            } else {
//                    Bank bank = BankDtoMapper.INSTANCE.create(salecreate);
//                Bank bank = BankDtoMapper.INSTANCE.create(salecreate);

                Sales sales = BankDtoMapper.INSTANCE.createSales(salecreate);

            }
        }





//    @Transactional
//    public void responseList(BankDto.SaleResponse saleresponse) throws NullPointerException {
//        if (saleresponse == null) {
//            throw new NullPointerException("값이 없습니다 ..");
//        } else {
//            List<BankDto.SaleResponse> sales = BankDtoMapper.INSTANCE.toResponse(saleresponse);
//        }


//    @Transactional
//
//    public void responseBank(BankDto.Response response){
//        if(response.getBaseRate() >= 0 ){
//            response.setRate(response.getBaseRate()+ response.getAddRate());
//            Bank bank = BankDtoMapper.INSTANCE.calRate(response);
//
//        } else {
//            throw new NullPointerException("값을 정확히 입력하세요");
//        }
//
//        if(response.getLoanMoney()>=0) {
//            response.setReturnMoney((long) ((response.getLoanMoney() * response.getRate() / 12) + (response.getLoanMoney() / (response.getReturnYear()) * 12)));
//        }else {
//            throw new NullPointerException("값을 정확히 입력하세요...");
//        }
//

//    @Transactional
//    public void calRate(BankDto.Response response){
//        if(response.getRate()<0){
//            throw new NullPointerException("정확한 값을 입력해주세요");
//        } else{
//            Bank bank = BankDtoMapper.INSTANCE.calRate(response);
//
//        }
//
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



//    public BankDto.Update update( BankDto.Update update) {
//        return update;
//    }

    public List<BankDto.Response> getbank(BankDto.Request request) {
        return null;
    }

    public BankDto.Response detail (Long id){
        Bank bank = bankRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 값입니다."));
        BankDto.Response response = bank.toResponse();
        if(bank.getDiscountRows() != null && bank.getDiscountRows().size()> 0 ) {
            response.setDiscountrows(bank.getDiscountRows().stream().map(Sales::toResponse).collect(Collectors.toList()));
        }
        return response;
    }
}
