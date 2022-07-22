package com.template.api.apps.postcodes.controller;


import com.template.api.apps.postcodes.dto.PostcodeDto.Response;
import com.template.api.apps.postcodes.dto.AptPriceDto;

import com.template.api.apps.postcodes.dto.PostcodeDto;
import com.template.api.apps.postcodes.service.PostCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postcodes")
public class PostcodeController {

    private final PostCodeService postCodeService;

    @GetMapping("/search")
    public List<Response> search(PostcodeDto.Request request) {
        return postCodeService.findByReq(request);
    }

    @GetMapping("/kb/{kbid}")
    public List<AptPriceDto.Simple> kbsearch(@PathVariable("kbid") String kbId) {
        return postCodeService.getSimpleByKbId(kbId);
    }

    @GetMapping("/official/{mid}")
    public List<AptPriceDto.OfficialItemDong> officialSearch(@PathVariable("mid") String mid) {
        return postCodeService.getOfficial(mid);
    }

}
