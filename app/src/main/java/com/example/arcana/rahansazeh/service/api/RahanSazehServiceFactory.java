package com.example.arcana.rahansazeh.service.api;

import com.example.arcana.rahansazeh.service.DataEntryService;
import com.example.arcana.rahansazeh.service.LoginService;
import com.example.arcana.rahansazeh.service.ProjectListService;
import com.example.arcana.rahansazeh.service.ServiceFactory;
import com.example.arcana.rahansazeh.service.VehicleService;
import com.example.arcana.rahansazeh.service.VehicleTypeService;

/**
 * Created by arcana on 11/23/17.
 */

public class RahanSazehServiceFactory implements ServiceFactory {
    private RahanSazehApiClient client;

    public RahanSazehServiceFactory() {
        this.client = new RahanSazehApiClient();
    }

    @Override
    public LoginService createLogin() {
        return client;
    }

    @Override
    public ProjectListService createProjectList() {
        return client;
    }

    @Override
    public VehicleTypeService createVehicleType() {
        return client;
    }

    @Override
    public VehicleService createVehicle() {
        return client;
    }

    @Override
    public DataEntryService createDataEntry() {
        return client;
    }
}
