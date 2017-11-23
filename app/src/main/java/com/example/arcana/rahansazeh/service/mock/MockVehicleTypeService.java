package com.example.arcana.rahansazeh.service.mock;

import com.example.arcana.rahansazeh.service.VehicleTypeService;
import com.example.arcana.rahansazeh.service.data.ServiceVehicleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public class MockVehicleTypeService extends BaseMockService implements VehicleTypeService {
    @Override
    public List<ServiceVehicleType> getVehicleTypes(String userName, String projectLineId) throws Exception {
        sleep();

        ArrayList<ServiceVehicleType> vehicleTypeList = new ArrayList<>();

        vehicleTypeList.add(new ServiceVehicleType("1", "ون"));
        vehicleTypeList.add(new ServiceVehicleType("2","پژو (۴۰۵-روآ-آردی)"));
        vehicleTypeList.add(new ServiceVehicleType("3","سمند"));
        vehicleTypeList.add(new ServiceVehicleType("4","پیکان"));
        vehicleTypeList.add(new ServiceVehicleType("5","سایر"));

        return vehicleTypeList;
    }
}
