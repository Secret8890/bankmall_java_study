package com.template.api.apps.profile.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.template.api.apps.profile.dto.ProfileDto;
import com.template.api.apps.profile.service.ProfileService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {" 프로필관리 "})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v2/profiles")

public class ProfileController {

    private final ProfileService profileService;

    @ApiOperation(value = " 프로필 정보 조회 ")
    @GetMapping
    public PagableDto.Response<ProfileDto.Response> getProfiles(ProfileDto.Request request) {
        return profileService.getPageProfiles(request);
    }

    @ApiOperation(value = " 프로필 등록 ")
    @PostMapping
    public void createProfile(@RequestBody ProfileDto.Create create) throws NotFoundException {
        profileService.createProfile(create);
    }

    @ApiOperation(value = " 프로필 수정")
    @PutMapping("/{id}")
    public  void updateProfile(@PathVariable Long id , @RequestBody ProfileDto.Update update) throws NotFoundException{
        profileService.update(id , update);
    }
}