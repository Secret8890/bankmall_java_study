package com.template.api.apps.partnerlogo.domain;


import com.bankmall.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PartnerLogoRepository extends BaseRepository {

    Optional<PartnerLogo> findById(Long id);

}