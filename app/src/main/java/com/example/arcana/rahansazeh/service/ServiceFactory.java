package com.example.arcana.rahansazeh.service;

/**
 * Created by arcana on 11/10/17.
 */

public interface ServiceFactory {
    LoginService createLogin();
    ProjectListService createProjectList();
    VehicleTypeService createVehicleType();
    VehicleService createVehicle();
    DataEntryService createDataEntry();
}
