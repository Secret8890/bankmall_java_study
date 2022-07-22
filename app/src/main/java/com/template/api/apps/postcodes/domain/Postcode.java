package com.template.api.apps.postcodes.domain;

import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@Entity
public class Postcode extends BaseDomainWithId {

   private String mid; // 관리번호

    private String bcode;

    private String sido;

    private String sigungu;

    private String epMyun;

    private String ri;

    private String roadName;

    private String buildMainCode;

    private String buildSubCode;

    private String dong;

    private String bunji;

    private String ho;

    private String buildName;

    private String buildType;

    private String type;

    private String searchText;

    private String searchTextRoad;

    private Boolean master;

}
