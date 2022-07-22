package com.template.api.apps.postcodes.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OfficialLandRepository extends BaseRepository<OfficialLand> {

    @Query(value = "select dong" +
            "                 , dong_code                                          as dongCode" +
            "                 , ho" +
            "                 , ho_code                                            as hoCode" +
            "                 , official_price                                     as officialPrice" +
            "                 , CAST(coalesce(area_size, '0') AS DOUBLE PRECISION) as areaSize" +
            "            from official_land" +
            "            where bcode = :bcode" +
            "              and concat(bon_bun, '-', bu_bun) in (" +
            "                select concat(bunji, '-', ho)" +
            "                from postcode" +
            "                where mid = :mid" +
            "                  AND master = true" +
            "            )" +
            "            order by length(dong) asc, dong asc, length(ho) asc, ho asc" , nativeQuery = true)
    List<Map<String, Object>> getObject(@Param("bcode") String bcode, @Param("mid") String mid);

    // todo: bcode 대신 addr2 그리고 Addr2로 변경
}
