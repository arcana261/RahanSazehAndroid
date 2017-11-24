package com.example.arcana.rahansazeh.service;

import com.example.arcana.rahansazeh.service.data.ServiceVehicle;
import com.example.arcana.rahansazeh.service.data.ServiceVehicleChange;

import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public interface VehicleService {
    Long getVehicleCount(String userName, String projectId) throws Exception;
    List<ServiceVehicle> getVehicles(String userName,
                                     String projectId,
                                     Long page,
                                     Long pageSize) throws Exception;

    Long getVehicleChangeCount(String userName, String projectId, Long epoch) throws Exception;

    List<ServiceVehicleChange> getVehicleChanges(String userName, String projectId,
                                                 Long epoch, Long page,
                                                 Long pageSize) throws Exception;
}
