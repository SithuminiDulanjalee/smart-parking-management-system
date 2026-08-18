package lk.ijse.parkingspaceservice.dto;

import lk.ijse.parkingspaceservice.entity.SpaceStatus;
import lk.ijse.parkingspaceservice.entity.VehicleType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ParkingSpaceResponse {

    private Long id;
    private String slotNumber;
    private String location;
    private BigDecimal hourlyRate;
    private VehicleType vehicleTypeAllowed;
    private SpaceStatus status;
    private Long currentReservedUserId;
    private LocalDateTime updatedAt;
}