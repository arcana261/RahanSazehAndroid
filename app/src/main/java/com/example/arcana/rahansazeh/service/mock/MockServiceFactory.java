package com.example.arcana.rahansazeh.service.mock;

import com.example.arcana.rahansazeh.service.LoginService;
import com.example.arcana.rahansazeh.service.ProjectListService;
import com.example.arcana.rahansazeh.service.ServiceFactory;
import com.example.arcana.rahansazeh.service.VehicleService;
import com.example.arcana.rahansazeh.service.VehicleTypeService;

/**
 * Created by arcana on 11/10/17.
 */

public class MockServiceFactory implements ServiceFactory {
    @Override
    public LoginService createLogin() {
        return new MockLoginService();
    }

    @Override
    public ProjectListService createProjectList() {
        return new MockProjectListService();
    }

    @Override
    public VehicleTypeService createVehicleType() {
        return new MockVehicleTypeService();
    }

    @Override
    public VehicleService createVehicle() {
        return new MockVehicleService();
    }
}
