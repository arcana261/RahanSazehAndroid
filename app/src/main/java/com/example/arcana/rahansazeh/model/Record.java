package com.example.arcana.rahansazeh.model;

import java.io.Serializable;

/**
 * Created by arcana on 11/7/17.
 */

public class Record implements Serializable {
    private boolean passengerUnloaded;
    private boolean passengerLoaded;
    private LicensePlate licensePlate;
    private String vehicleType;
    private Time arrivalTime;
    private Time departureTime;
    private int passengerLoadCount;
    private int passengerUnloadCount;

    public Record() {
        this.passengerLoaded = false;
        this.passengerUnloaded = false;
        this.licensePlate = null;
        this.vehicleType = null;
        this.arrivalTime = null;
        this.departureTime = null;
        this.passengerLoadCount = 0;
        this.passengerUnloadCount = 0;
    }

    public boolean isPassengerUnloaded() {
        return this.passengerUnloaded;
    }

    public void setPassengerUnloaded(boolean value) {
        this.passengerUnloaded = value;
    }

    public boolean isPassengerLoaded() {
        return this.passengerLoaded;
    }

    public void setPassengerLoaded(boolean value) {
        this.passengerLoaded = value;
    }

    public LicensePlate getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(LicensePlate licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Time getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(Time value) {
        this.arrivalTime=  value;
    }

    public Time getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(Time value) {
        this.departureTime = value;
    }

    public int getPassengerLoadCount() {
        return this.passengerLoadCount;
    }

    public void setPassengerLoadCount(int passengerLoadCount) {
        this.passengerLoadCount = passengerLoadCount;
    }

    public int getPassengerUnloadCount() {
        return this.passengerUnloadCount;
    }

    public void setPassengerUnloadCount(int passengerUnloadCount) {
        this.passengerUnloadCount = passengerUnloadCount;
    }
}
