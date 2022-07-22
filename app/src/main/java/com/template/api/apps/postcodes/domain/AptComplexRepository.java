package com.template.api.apps.postcodes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AptComplexRepository extends JpaRepository<AptComplex, String>,
        JpaSpecificationExecutor<AptComplex> {

    Optional<AptComplex> findTop1ByKbCode(String kbCode);

    @Query(value = "select name," +
            "                   is_ticket," +
            "                   road_address," +
            "                   road_address_details," +
            "                   kb_code," +
            "                   id," +
            "                   bcode," +
            "                   total_live," +
            "                   road_build_mgr_no," +
            "                   addr2," +
            "                   addr3," +
            "                   addr4," +
            "                   addr5," +
            "                   addr6," +
            "                   addr7," +
            "                   is_villa" +
            "            from apt_complex" +
            "            where (" +
            "                        name ilike :keyword OR" +
            "                        search_text ilike :keyword OR" +
            "                        search_text_road ilike :keyword" +
            "                )" +
            "              AND (" +
            "                :id IS NULL OR id LIKE cast(:id AS text)" +
            "                )" +
            "              AND (" +
            "                :isVilla IS NULL OR is_villa = cast(:isVilla AS text)" +
            "                )" +
            "            order by word_similarity(name, :name) desc," +
            "                     word_similarity(search_text, :name) desc," +
            "                     word_similarity(search_text_road, :name) desc," +
            "                     name asc limit :limitsize", nativeQuery = true)
    List<Map<String, Object>> getSearchData(
            @Param("keyword") String keyword,
            @Param("id") String id,
            @Param("isVilla") String isVilla,
            @Param("name") String name,
            @Param("limitsize") Integer limitsize
    );

    Optional<AptComplex> findFirstByRoadBuildMgrNo(String mid);

    @Query(value = "SELECT MIN(size) min, MAX(size) max FROM apt_size_type as ast WHERE ast.apt_complex_id = :id", nativeQuery = true)
    Map<String, Object> getMinMaxSpace(@Param("id") String id);


}
