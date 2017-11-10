package com.example.arcana.rahansazeh.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by arcana on 11/10/17.
 */

@Entity
public class User {
    @Id
    private Long id;

    @Index(unique = true)
    @NotNull
    private String nationalCode;

    @Generated(hash = 1333849346)
    public User(Long id, @NotNull String nationalCode) {
        this.id = id;
        this.nationalCode = nationalCode;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
