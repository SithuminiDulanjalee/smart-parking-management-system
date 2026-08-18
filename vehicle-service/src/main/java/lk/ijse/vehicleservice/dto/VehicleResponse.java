package lk.ijse.vehicleservice.dto;

import lk.ijse.vehicleservice.entity.VehicleStatus;
import lk.ijse.vehicleservice.entity.VehicleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VehicleResponse {

    private Long id;
    private String licensePlate;
    private VehicleType vehicleType;
    private Long userId;
    private String ownerName;
    private String ownerEmail;
    private VehicleStatus status;
    private LocalDateTime createdAt;
}