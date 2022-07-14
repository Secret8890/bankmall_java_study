package com.template.api.apps.partnerlogo.controller;

import com.bankmall.apps.partnerlogo.dto.PartnerLogoDto;
import com.bankmall.apps.partnerlogo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"[CMS] 파트너사 로고 관리"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cmspartnerLogo")

public class PartnerLogoController {

    private final PartnerLogoService partnerlogoservice;

    @ApiOperation(value = "CMS 파트너사 로고 등록")
    @PostMapping

    public void createPartnerLogo(@RequestBody PartnerLogoDto.Create create) {
        partnerlogoservice.createpartnerlogo(create);
    }

    @ApiOperation(value = "CMS 파트너사 로고 수정")
    @PutMapping

    public void updatePartnerLogo(@PathVariable Long id , @RequestBody PartnerLogoDto.Update update){
            partnerlogoservice.update(id,update);
    }

    @ApiOperation(value = "CMS 파트너사 로고 삭제")
    @DeleteMapping

    public  void deletePartnerLogo(@PathVariable Long id){
        partnerlogoservice.delete(id);
    }

    @ApiOperation(value = "CMS 파트너사 로고 상세조회")
    @GetMapping
    public void detailPartnerLogo(@PathVariable Long id){
        partnerlogoservice.detail(id);
    }

    }




