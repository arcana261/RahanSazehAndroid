package com.example.arcana.rahansazeh.service.data;

/**
 * Created by arcana on 11/10/17.
 */

public class ServiceVehicleType {
    private String id;
    private String title;

    public ServiceVehicleType(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
