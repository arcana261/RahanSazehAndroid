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
    public List<ServiceVehicleType> getVehicleTypes(String userName, long projectLineId) throws Exception {
        sleep();

        ArrayList<ServiceVehicleType> vehicleTypeList = new ArrayList<>();

        vehicleTypeList.add(new ServiceVehicleType(1l, "ون"));
        vehicleTypeList.add(new ServiceVehicleType(2l,"پژو (۴۰۵-روآ-آردی)"));
        vehicleTypeList.add(new ServiceVehicleType(3l,"سمند"));
        vehicleTypeList.add(new ServiceVehicleType(4l,"پیکان"));
        vehicleTypeList.add(new ServiceVehicleType(5l,"سایر"));

        return vehicleTypeList;
    }
}
