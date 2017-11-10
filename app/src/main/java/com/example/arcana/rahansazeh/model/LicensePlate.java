package com.example.arcana.rahansazeh.model;

import java.io.Serializable;

/**
 * Created by arcana on 11/7/17.
 */

public class LicensePlate implements Serializable {
    private String left;
    private String type;
    private String right;

    public LicensePlate(String left, String right, String type) {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getType() {
        return type;
    }
}
