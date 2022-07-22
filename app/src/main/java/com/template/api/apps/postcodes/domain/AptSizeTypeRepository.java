package com.template.api.apps.postcodes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AptSizeTypeRepository extends JpaRepository<AptSizeType, AptSizeType.PK>,
        JpaSpecificationExecutor<AptSizeType> {
}
