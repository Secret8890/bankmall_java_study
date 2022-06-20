package com.template.api.apps.banks.service;


import com.template.api.apps.banks.domain.Bank;
import com.template.api.apps.banks.domain.BankRepository;
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

    public void createBank(BankDto.Create create){
        if(create.getBankName() == null ){
            throw new NullPointerException("금융사명을 입력해주세요 ..");
        } else {
            Bank bank = BankDtoMapper.INSTANCE.create(create);
            bankRepository.save(bank);
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
