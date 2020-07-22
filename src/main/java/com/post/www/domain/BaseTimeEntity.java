package com.post.www.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdate;

    @LastModifiedDate
    private LocalDateTime updatedate;

    public LocalDateTime getCreatedDate() {
        return createdate;
    }

    public LocalDateTime getModifiedDate() {
        return updatedate;
    }
}
