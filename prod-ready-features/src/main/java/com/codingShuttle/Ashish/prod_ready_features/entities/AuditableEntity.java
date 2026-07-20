package com.codingShuttle.Ashish.prod_ready_features.entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // In Spring Boot and JPA (Hibernate), @MappedSuperclass is an annotation used to inherit fields (like database columns) from a parent class without making that parent class an actual database entity.
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // Step-2: Make the class which you want these features as EntityListeners.
@Audited
public class AuditableEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @CreatedBy
    private String createdBy; // usually we store id in it.

    @LastModifiedBy
    private String updatedBy;
}
