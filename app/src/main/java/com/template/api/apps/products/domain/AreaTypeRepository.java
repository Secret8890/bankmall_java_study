package com.template.api.apps.products.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaTypeRepository extends JpaRepository<AreaType, String>,
        JpaSpecificationExecutor<AreaType> {

    AreaType findByBcode(String bcode);

    @Query("FROM AreaType WHERE bcode in (:bcodes)")
    List<AreaType> getByBcodes(@Param("bcodes") List<String> bcodes);

    // todo: 여기는 못 건드림 , 주소 건들때 참고 bcode

    //SIDO
    @Query("SELECT DISTINCT sido FROM AreaType ORDER BY sido")
    List<String> getBySidos();

    //GUGUN
    @Query("SELECT DISTINCT gugun FROM AreaType WHERE sido = :sido ORDER BY gugun")
    List<String> getByGuguns(@Param("sido") String sido);

    //DODONG
    @Query("SELECT DISTINCT dodong FROM AreaType WHERE sido = :sido and gugun = :gugun ORDER BY dodong")
    List<String> getByDodongs(@Param("sido") String sido, @Param("gugun") String gugun);

    //FULL NAME
    @Query("SELECT DISTINCT fullname FROM AreaType")
    List<String> getByFullNames();

    Optional<AreaType> findBySidoAndGugunAndDodongAndEupri(String sido, String gugun, String dodong, String eupri);

}
