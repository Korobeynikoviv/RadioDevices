package com.radiodevices.wifianalyzer.enitity;

import java.util.UUID;

public class Entity implements Data {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Entity() {
        this.id = UUID.randomUUID();
    }
}
