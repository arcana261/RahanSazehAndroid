package com.example.arcana.rahansazeh.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by arcana on 11/10/17.
 */

@Entity
public class VehicleType {
    @Id
    private Long id;

    @NotNull
    @Index(unique = true)
    private String title;

    @NotNull
    @Index(unique = true)
    private Long externalId;

    @Generated(hash = 1249135248)
    public VehicleType(Long id, @NotNull String title, @NotNull Long externalId) {
        this.id = id;
        this.title = title;
        this.externalId = externalId;
    }

    @Generated(hash = 1440934529)
    public VehicleType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getExternalId() {
        return this.externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }
}
