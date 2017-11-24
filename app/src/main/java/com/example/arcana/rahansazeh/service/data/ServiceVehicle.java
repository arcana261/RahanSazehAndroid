package com.example.arcana.rahansazeh.service.data;

/**
 * Created by arcana on 11/10/17.
 */

public class ServiceVehicle {
    private String id;
    private int licensePlateLeft;
    private char licensePlateType;
    private int licensePlateRight;
    private int licensePlateNationalCode;
    private String vehicleTypeId;
    private String projectId;

    public ServiceVehicle(String id, int licensePlateLeft, char licensePlateType,
                          int licensePlateRight, int licensePlateNationalCode,
                          String vehicleTypeId, String projectId) {
        this.id = id;
        this.licensePlateLeft = licensePlateLeft;
        this.licensePlateType = licensePlateType;
        this.licensePlateRight = licensePlateRight;
        this.licensePlateNationalCode = licensePlateNationalCode;
        this.vehicleTypeId = vehicleTypeId;
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLicensePlateLeft() {
        return licensePlateLeft;
    }

    public void setLicensePlateLeft(int licensePlateLeft) {
        this.licensePlateLeft = licensePlateLeft;
    }

    public char getLicensePlateType() {
        return licensePlateType;
    }

    public void setLicensePlateType(char licensePlateType) {
        this.licensePlateType = licensePlateType;
    }

    public int getLicensePlateRight() {
        return licensePlateRight;
    }

    public void setLicensePlateRight(int licensePlateRight) {
        this.licensePlateRight = licensePlateRight;
    }

    public int getLicensePlateNationalCode() {
        return licensePlateNationalCode;
    }

    public void setLicensePlateNationalCode(int licensePlateNationalCode) {
        this.licensePlateNationalCode = licensePlateNationalCode;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
