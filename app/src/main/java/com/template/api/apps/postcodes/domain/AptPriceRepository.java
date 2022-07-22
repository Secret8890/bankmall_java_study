package com.template.api.apps.postcodes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface AptPriceRepository extends JpaRepository<AptPrice, AptPrice.PK>,
        JpaSpecificationExecutor<AptPrice> {

    @Query(value = " SELECT A.name" +
            "                 , A.nom_avr_deal_prc" +
            "                 , B.size" +
            "                 , A.addr2" +
            "                 , A.addr3" +
            "                 , A.addr4" +
            "                 , C.kb_code" +
            "            FROM apt_price A" +
            "                     LEFT JOIN" +
            "                 apt_size_type B ON A.apt_complex_id = B.apt_complex_id AND A.size_no = B.size_no" +
            "                     LEFT JOIN" +
            "                 apt_complex C ON A.apt_complex_id = C.id" +
            "            WHERE (:aptComplexId IS NULL OR C.id LIKE :aptComplexId)" +
            "              AND (:isVilla IS NULL OR C.is_villa = :isVilla)" +
            "              AND A.addr2 = :addr2" +
            "            ORDER BY ABS(A.nom_avr_deal_prc - :nomAvrDealPrc) ASC limit 100;", nativeQuery = true)
    List<Tuple> getAroundPrice(@Param("aptComplexId") String aptComplexId, @Param("isVilla") String isVilla, @Param("addr2") String addr2, @Param("nomAvrDealPrc") long nomAvrDealPrc);

    @Query(value= "SELECT A.name" +
            "                 , A.nom_avr_deal_prc" +
            "                 , B.size" +
            "                 , A.addr2" +
            "                 , A.addr3" +
            "                 , A.addr4" +
            "                 , C.kb_code" +
            "            FROM apt_price A" +
            "                     LEFT JOIN" +
            "                 apt_size_type B ON A.apt_complex_id = B.apt_complex_id AND A.size_no = B.size_no" +
            "                     LEFT JOIN" +
            "                 apt_complex C ON A.apt_complex_id = C.id" +
            "            WHERE (:aptComplexId IS NULL OR C.id LIKE :aptComplexId)" +
            "              AND (:isVilla IS NULL OR C.is_villa = :isVilla)" +
            "              AND A.addr2 = :addr2" +
            "            ORDER BY ABS(B.size - :size) ASC limit 100;"  ,nativeQuery = true)
    List<Tuple> getAroundSize(@Param("aptComplexId") String aptComplexId, @Param("isVilla") String isVilla, @Param("addr2") String addr2, @Param("size") double size);


}
