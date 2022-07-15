package com.template.api.apps.partnerlogo.controller;


import com.template.api.apps.partnerlogo.dto.PartnerLogoDto;
import com.template.api.apps.partnerlogo.service.PartnerLogoService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Api(tags = "[CMS] Partner Logo system")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partnerLG")
@Secured({"ROLE_Partner"})
public class PartnerLogoController {

    private final PartnerLogoService partnerLogoService;

    @ApiOperation(value = "LOGO 등록")
    @PostMapping
    public void updatePartnerLogo (PartnerLogoDto.Create create){
        partnerLogoService.createPartnerLogo(create);
    }

    @ApiOperation(value = "LOGO 수정")
    @PutMapping
    public void updatePartnerLogo(Long id, PartnerLogoDto.Update update){
        partnerLogoService.updatePartnerLogo(id, update);
    }

    @ApiOperation(value = "LOGO 삭제")
    @DeleteMapping
    public void deletePartnerLogo(Long id){
        partnerLogoService.delete(id);
    }

    @ApiOperation(value = "모두 조회")
    @GetMapping
    public PagableDto.Response<PartnerLogoDto.Response>getPartnerLogos(PartnerLogoDto.Request request){
        return partnerLogoService.getPagePartnerLogos(request);
    }


}
