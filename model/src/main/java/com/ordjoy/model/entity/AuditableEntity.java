package com.ordjoy.model.entity;

import com.ordjoy.model.entity.listener.AuditDatesListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditDatesListener.class)
public abstract class AuditableEntity<K extends Serializable> extends BaseEntity<K> {

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}