package com.example.arcana.rahansazeh.service.data;

/**
 * Created by arcana on 11/24/17.
 */

public class ServiceVehicleChange {
    private Long epoch;
    private String eventType;
    private String vehicleId;
    private ServiceVehicle vehicle;

    public ServiceVehicleChange(Long epoch, String eventType,
                                String vehicleId, ServiceVehicle vehicle) {
        this.epoch = epoch;
        this.eventType = eventType;
        this.vehicleId = vehicleId;
        this.vehicle = vehicle;
    }

    public ServiceVehicleChange() {
    }

    public Long getEpoch() {
        return epoch;
    }

    public String getEventType() {
        return eventType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public ServiceVehicle getVehicle() {
        return vehicle;
    }
}
