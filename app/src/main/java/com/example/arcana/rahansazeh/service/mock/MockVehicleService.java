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
    public List<ServiceVehicle> getVehicles(String userName, String projectLineId) throws Exception {
        sleep();

        ArrayList<ServiceVehicle> result = new ArrayList<>();

        result.add(new ServiceVehicle("1",
                23, 'ت', 764,
                10, "1" ));

        result.add(new ServiceVehicle("2",
                46, 'ت', 234,
                10, "2" ));

        result.add(new ServiceVehicle("3",
                80, 'ت', 912,
                10, "1" ));

        return result;
    }
}
