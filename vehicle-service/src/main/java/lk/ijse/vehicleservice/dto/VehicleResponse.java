package lk.ijse.vehicleservice.dto;

import lk.ijse.vehicleservice.entity.Vehicle;

public class VehicleResponse {

    private Long id;
    private String registrationNumber;
    private String vehicleType;
    private Long userId;
    private String status;

    public VehicleResponse() {
    }

    public VehicleResponse(
            Long id,
            String registrationNumber,
            String vehicleType,
            Long userId,
            String status) {

        this.id = id;
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
        this.userId = userId;
        this.status = status;
    }

    public static VehicleResponse from(
            Vehicle vehicle) {

        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getVehicleType(),
                vehicle.getUserId(),
                vehicle.getStatus()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}