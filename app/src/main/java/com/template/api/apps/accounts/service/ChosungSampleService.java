package com.template.api.apps.accounts.service;

import com.google.common.base.CaseFormat;
import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.domain.AccountRepository;
import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.apps.accounts.dto.AccountDtoMapper;
import com.template.api.apps.accounts.dto.ChosungSampleDto;
import com.template.api.configs.QueryDSLConfig;
import com.template.api.utils.ChosungSearchQuery;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * 초성 검색시 SQL 쿼리 작성 샘플입니다.
 *
 * @author ISSAC
 * @version 1.0.0
 * 작성일 2022-03-11
 *
**/

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChosungSampleService {

    private final AccountRepository accountRepository;
    private final QueryDSLConfig queryDSLConfig;    //EntityManager 객체반환 위해 선언

    public PagableDto.Response<AccountDto.Response> getChosungAccounts(ChosungSampleDto.Request request){

        //페이징 처리
        if(request.getOrder() == null){
            request.setOrder(PagableDto.OrderType.DESC);
        }
        String orderFiled = "created_at";
        if (request.getSort() != null) {
            orderFiled = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getSort());  //스네이크 케이스로 변환
        }

        //SQL 작성
        String sql = "SELECT * FROM account a ";
        if(request.getKeyword() != null){
            sql += "WHERE " + ChosungSearchQuery.searchSql(request.getKeyword(), request.getField());  //초성 조건절
        }
        sql += " ORDER BY " + orderFiled + " "  + request.getOrder().name();    //정렬필드
        sql += " LIMIT " + request.getLimit() + " OFFSET " + request.getOffset();   //limit

        Query nativeQuery = queryDSLConfig.getEntityManager().createNativeQuery(sql, Account.class);    //Account 클래스로 매핑정보를 함께 입력해줌
        List<AccountDto.Response> results = AccountDtoMapper.INSTANCE.toResponses(nativeQuery.getResultList()); //Account -> AccountDto

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());
        Page<?> response = new PageImpl<>(results, pageRequest, results.size());

        return PagableDto.Response.of(response, response.getContent());
    }




}
