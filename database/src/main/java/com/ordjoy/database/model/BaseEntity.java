package com.ordjoy.database.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import java.io.Serializable;

@Setter
@Getter
@ToString
@MappedSuperclass
public abstract class BaseEntity<K extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected K id;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    protected EntityState entityState;

    @PreRemove
    public void deleteEntity() {
        this.entityState = EntityState.NOT_ACTIVE;
    }
}