package com.nossogame.bancoimobiliario.model;

import jakarta.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractModel {

    @Id
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrePersist
    protected void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
