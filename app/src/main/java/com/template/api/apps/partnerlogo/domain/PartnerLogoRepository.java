package com.template.api.apps.partnerlogo.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerLogoRepository extends BaseRepository<PartnerLogo> {

    void deleteById(Long id);

}
