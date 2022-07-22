package com.template.api.apps.postcodes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostcodeRoadNameRepository extends
        JpaRepository<PostcodeRoadName, PostcodeRoadName.PK> {

    List<PostcodeRoadName> findTop1ByIdId(String rc);
}
