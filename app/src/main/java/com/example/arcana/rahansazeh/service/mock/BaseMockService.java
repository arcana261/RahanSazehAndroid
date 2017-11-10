package com.example.arcana.rahansazeh.service.mock;

/**
 * Created by arcana on 11/10/17.
 */

public class BaseMockService {
    protected void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
