package com.example.arcana.rahansazeh.service.mock;

import com.example.arcana.rahansazeh.service.LoginService;

/**
 * Created by arcana on 11/10/17.
 */

public class MockLoginService extends BaseMockService implements LoginService {
    @Override
    public boolean Login(String userName) throws Exception {
        sleep();

        return userName.equals("0010220887");
    }
}
