package com.template.api.jpa.base;

import com.template.api.apps.Account.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseLogDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    protected Long id;

    private String remoteAddr;

    private String userAgent;

    private String origin;

    private String requestUri;

    private String method;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private Account createdBy; // 등록자 정보

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private Account updatedBy; // 수정자 정보

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedOn;

    @PrePersist
    public void prePersist() {
        HttpServletRequest request = getCurrentHttpRequest().orElse(null);
        if (request != null) {
            this.remoteAddr = request.getRemoteAddr();

            this.userAgent = request.getHeader("User-Agent");
            if (this.userAgent != null && this.userAgent.length() > 255) {
                this.userAgent = this.userAgent.substring(0, 255);
            }

            this.requestUri = request.getRequestURI();
            if (this.requestUri != null && this.requestUri.length() > 255) {
                this.requestUri = this.requestUri.substring(0, 255);
            }

            this.method = request.getMethod();

            this.origin = request.getHeader("Origin");
            if (this.origin != null && this.origin.length() > 255) {
                this.origin = this.origin.substring(0, 255);
            }
        }
    }

    @Transient
    protected static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }


}
