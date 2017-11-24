package com.example.arcana.rahansazeh.service;

import com.example.arcana.rahansazeh.service.data.ServiceVehicleType;

import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public interface VehicleTypeService {
    List<ServiceVehicleType> getVehicleTypes(String userName, String projectId) throws Exception;
}
