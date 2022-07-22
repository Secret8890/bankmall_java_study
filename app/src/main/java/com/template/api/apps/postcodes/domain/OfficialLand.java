package com.template.api.apps.postcodes.domain;


import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class OfficialLand extends BaseDomainWithId {

    private String stdYear; //기준연도
    private String stdMonth; //기준월
    private String bcode; //법정동코드
    private String newAddr; //도로명 주소
    private String siDo; //시도
    private String siGunGu; //시군구
    private String eupMyeon; //읍면
    private String dongLi; //동리
    private String specialCode; //특수지코드
    private String bonBun; //본번
    private String buBun; //부번
    private String specialName; //특수지명
    private String buildingName; //단지명
    private String dong; //동명
    private String ho; //호명
    private String areaSize; //전용면적
    private Long officialPrice; //공시가격
    private String dangiCode; //단지코드
    private String dongCode; //동코드
    private String hoCode; //호코드

}
