package com.example.arcana.rahansazeh.service;

import com.example.arcana.rahansazeh.service.data.ServiceVehicle;

import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public interface VehicleService {
    List<ServiceVehicle> getVehicles(String userName, long projectLineId);
}
