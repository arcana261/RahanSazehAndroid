package com.example.arcana.rahansazeh.service;

import com.example.arcana.rahansazeh.model.OutgoingPassengerRecord;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;

/**
 * Created by arcana on 11/19/17.
 */

public interface DataEntryService {
    void addVehicleRecord(OutgoingVehicleRecord record) throws Exception;
    void addPassengerRecord(OutgoingPassengerRecord record) throws Exception;
}
