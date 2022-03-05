package com.ordjoy.database.model.listener;

import com.ordjoy.database.model.AuditableEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class AuditDatesListener {

    @PrePersist
    public void prePersist(AuditableEntity<?> entity) {
        entity.setCreatedAt(Instant.now());
    }

    @PreUpdate
    public void preUpdate(AuditableEntity<?> entity) {
        entity.setUpdatedAt(Instant.now());
    }
}