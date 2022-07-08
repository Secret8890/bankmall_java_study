package com.template.api.apps.profile.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends BaseRepository<Profile> {

    Optional<Profile> findById(String userId);

}
