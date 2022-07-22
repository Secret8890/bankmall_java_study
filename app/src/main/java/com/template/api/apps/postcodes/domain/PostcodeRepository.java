package com.template.api.apps.postcodes.domain;

import com.template.api.jpa.base.BaseRepository;

import java.util.Optional;

public interface PostcodeRepository extends BaseRepository<Postcode> {

    Optional<Postcode> findFirstByMid(String mid);
}
