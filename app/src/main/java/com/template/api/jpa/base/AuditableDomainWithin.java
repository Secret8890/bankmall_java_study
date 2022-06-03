package com.template.api.jpa.base;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class AuditableDomainWithin<T> extends BaseDomainWithId {

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private T createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private T updatedBy;

}
