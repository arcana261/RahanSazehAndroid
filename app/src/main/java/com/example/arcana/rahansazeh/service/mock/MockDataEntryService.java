package com.example.arcana.rahansazeh.service.mock;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.service.DataEntryService;

/**
 * Created by arcana on 11/20/17.
 */

public class MockDataEntryService extends BaseMockService implements DataEntryService {
    @Override
    public void addVehicleRecord(OutgoingVehicleRecord record) {
        sleep();
    }

    @Override
    public void addPassengerRecord(OutgoingPassengerRecord record) {
        sleep();
    }
}
