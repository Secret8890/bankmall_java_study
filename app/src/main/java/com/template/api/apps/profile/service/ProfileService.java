package com.template.api.apps.profile.service;

import com.template.api.apps.profile.domain.Profile;
import com.template.api.apps.profile.domain.ProfileRepository;
import com.template.api.apps.profile.dto.ProFileDtoMapper;
import com.template.api.apps.profile.dto.ProfileDto;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)

public class ProfileService {

    private final ProfileRepository profileRepository;


    public PagableDto.Response<ProfileDto.Response> getPageProfiles(ProfileDto.Request request) {
        //region 정렬
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())) {
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);

        Page<Profile> items = profileRepository.findAll(r.output(), pageRequest);

        PagableDto.Response<ProfileDto.Response> pages = PagableDto.Response.of(items);


        return pages;
    }

    @Transactional 
    public void createProfile(ProfileDto.Create create) {

        if(create == null){
            throw new NullPointerException("입력된 값이 없습니다 ");
        }else {

            Profile profile = ProFileDtoMapper.INSTANCE.create(create);

            profileRepository.save(profile);
        }
    }

    public void update(Long id, ProfileDto.Update update) throws NullPointerException {

        Profile profile = profileRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 입니다."));

        ProFileDtoMapper.INSTANCE.update(update,profile);
    }

    public ProfileDto.Response detail (Long id){
        Profile profile = profileRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 값입니다."));
        ProfileDto.Response response = profile.toResponse();

        return response;
    }

}
