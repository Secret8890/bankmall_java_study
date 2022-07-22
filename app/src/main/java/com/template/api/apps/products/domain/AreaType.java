package com.template.api.apps.products.domain;

import com.template.api.apps.products.dto.AreaTypeDto;
import com.template.api.apps.products.dto.AreaTypeDtoMapper;
import com.template.api.jpa.base.BaseDomain;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AreaType extends BaseDomain implements DomainWithMapper {

    @Id
    private String bcode;

    @ColumnDefault("''")
    private String sido = "";;

    @ColumnDefault("''")
    private String gugun = "";;

    @ColumnDefault("''")
    private String dodong = "";;

    @ColumnDefault("''")
    private String eupri = "";

    private String fullname;

    @Enumerated(EnumType.STRING)
    private AREA_TYPE type = AREA_TYPE.ARTY004;

    @Override
    public AreaTypeDto.Response toResponse() {
        return AreaTypeDtoMapper.INSTANCE.entityToResponse(this);
    }

    public enum AREA_TYPE {
        ARTY001("투기지역"),
        ARTY002("투기과열지구"),
        ARTY003("조정대상지역"),
        ARTY004("기타지역");

        private String label;

        AREA_TYPE(String label) {
            this.label = label;
        }

    }

}
