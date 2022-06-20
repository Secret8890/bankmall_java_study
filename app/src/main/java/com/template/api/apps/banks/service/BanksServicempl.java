package com.template.api.apps.banks.service;


import com.template.api.apps.banks.dto.BankDto;

import java.util.List;

public interface BanksServicempl {

    List<BankDto.Response> getReviews(BankDto.Request request);
    BankDto.Response getReviews(long id , String title , String content);
    void createReview(BankDto.Create create);

}
