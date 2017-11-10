package com.example.arcana.rahansazeh.service.mock;

import com.example.arcana.rahansazeh.service.VehicleService;
import com.example.arcana.rahansazeh.service.data.ServiceVehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arcana on 11/10/17.
 */

public class MockVehicleService extends BaseMockService implements VehicleService {
    @Override
    public List<ServiceVehicle> getVehicles(String userName, long projectLineId) {
        sleep();

        ArrayList<ServiceVehicle> result = new ArrayList<>();

        result.add(new ServiceVehicle(1l,
                23, 'ت', 764,
                10, 1l ));

        result.add(new ServiceVehicle(2l,
                46, 'ت', 234,
                10, 1l ));

        result.add(new ServiceVehicle(1l,
                80, 'ت', 912,
                10, 1l ));

        return result;
    }
}
