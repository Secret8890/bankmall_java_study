package com.template.api.apps.partnerlogo.service;

import com.bankmall.apps.partnerlogo.domain.PartnerLogo;
import com.bankmall.apps.partnerlogo.domain.PartnerLogoRepository;
import com.bankmall.apps.partnerlogo.dto.PartnerLogoDto;
import com.bankmall.apps.partnerlogo.dto.PartnerLogoDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class PartnerLogoService {

    private final PartnerLogoRepository partnerlogorepository;

    @Transactional
    public void createpartnerlogo(PartnerLogoDto.Create create){

        if (create==null){
            throw new NullPointerException("값이 없습니다 ");
        }else {
            PartnerLogo partnerlogo = PartnerLogoDtoMapper.INSTANCE.create(create);

            if(partnerlogo.getIsActive()==false){
                partnerlogo.setIsActive(false);
            }else {
                partnerlogo.setIsActive(true);
            }
            partnerlogorepository.save(partnerlogo);
        }
    }

    public PartnerLogoDto.Update update(Long id, PartnerLogoDto.Update update)  throws NullPointerException {

        PartnerLogo partnerlogo = partnerlogorepository.findById(id).orElseThrow(()->new NullPointerException("잘못된값"));

        PartnerLogoDtoMapper.INSTANCE.update(update,partnerlogo);

        if(partnerlogo.getIsActive()==false){
            partnerlogo.setIsActive(false);
        }else {
            partnerlogo.setIsActive(true);
        }
        return (PartnerLogoDto.Update) partnerlogorepository.save(update);
    }

    public PartnerLogoDto.Response detail (Long id){

        PartnerLogo partnerlogo = partnerlogorepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 값입니다."));
        PartnerLogoDto.Response response = partnerlogo.toResponse();

        return response;
    }

    @Transactional
    public void delete(Long id) {
        partnerlogorepository.deleteById(id);
    }


}