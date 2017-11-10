package com.example.arcana.rahansazeh.service.data;

/**
 * Created by arcana on 11/10/17.
 */

public class ServiceVehicle {
    private Long id;
    private int licensePlateLeft;
    private char licensePlateType;
    private int licensePlateRight;
    private int licensePlateNationalCode;
    private Long vehicleTypeId;

    public ServiceVehicle(Long id, int licensePlateLeft, char licensePlateType,
                          int licensePlateRight, int licensePlateNationalCode,
                          Long vehicleTypeId) {
        this.id = id;
        this.licensePlateLeft = licensePlateLeft;
        this.licensePlateType = licensePlateType;
        this.licensePlateRight = licensePlateRight;
        this.licensePlateNationalCode = licensePlateNationalCode;
        this.vehicleTypeId = vehicleTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Long vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
