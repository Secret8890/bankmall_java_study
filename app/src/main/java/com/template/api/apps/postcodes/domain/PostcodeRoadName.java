package com.template.api.apps.postcodes.domain;

import com.template.api.jpa.base.BaseDomain;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
public class PostcodeRoadName extends BaseDomain {

    @EmbeddedId
    private PK id;

    private String roadName; // 도로명

    private String roadEng; // 도로명 로마자

    private String sido;

    private String sidoEng;

    private String sigungu;

    private String sigunguEng;

    private String epMyun;

    private String epMyunEng;

    private String epMyunType;

    private String epMyunCode;

    private String used;

    private String changed;

    private String registAt;

    private String deleteAt;


    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class PK implements Serializable {

        private String id; // 관리번호

        private String seq; // 일련번호
    }

}
