package lk.ijse.paymentservice.dto;

import lombok.Data;

@Data
public class VehicleDTO {
    private Long id;
    private String licensePlate;
    private String vehicleType;
}