package com.template.api.apps.postcodes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AptSalesRepository extends JpaRepository<AptSales, Long>,
        JpaSpecificationExecutor<AptSales> {

    Long countById(String id);

    @Query( value = "<![CDATA[" +
            "            SELECT COUNT(*)" +
            "            FROM (" +
            "                     SELECT name" +
            "                     FROM apt_sales" +
            "                     WHERE type = :type" +
            "                       AND apt_complex_id is NULL" +
            "                       AND (" +
            "                         sigungu ilike :sigungu AND" +
            "            name ilike :name" +
            "                         )" +
            "                     GROUP BY name" +
            "                 ) K" +
            "            ]]>",nativeQuery = true)
    Long getNames(
            @Param("type") String type,
            @Param("sigungu") String sigungu,
            @Param("name") String name
    );

    @Modifying
    @Query(value = "<![CDATA[" +
            "            UPDATE apt_sales" +
            "            SET apt_complex_id = :id" +
            "            WHERE type = :type" +
            "              AND apt_complex_id is NULL" +
            "              AND (" +
            "                    (" +
            "                        sigungu ilike :sigungu AND" +
            "            name ilike :name AND" +
            "            (:roadAddress = '' OR road ilike :roadAddress)" +
            "                        )" +
            "                    OR (" +
            "                        road ilike :roadAddress" +
            "                        )" +
            "                )" +
            "            ]]>", nativeQuery = true)
    void updateToComplex(
            @Param("type") String type,
            @Param("sigungu") String sigungu,
            @Param("name") String name,
            @Param("roadAddress") String roadAddress,
            @Param("id") String id
    );

   List<AptSales> findAllByAptComplexOrderBySaledAtDesc(AptComplex aptComplex);

}
