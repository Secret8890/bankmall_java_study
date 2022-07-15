package com.template.api.apps.partnerlogo.domain;

import com.template.api.jpa.base.BaseRepository;

public interface PartnerLogoRepository extends BaseRepository<PartnerLogo> {

    void deleteById(Long id);

}
