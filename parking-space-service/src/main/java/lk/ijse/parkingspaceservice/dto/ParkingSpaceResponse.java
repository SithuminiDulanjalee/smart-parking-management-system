package lk.ijse.parkingspaceservice.dto;

import lk.ijse.parkingspaceservice.entity.ParkingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ParkingSpaceResponse {

    private Long id;
    private String slotNumber;
    private String location;
    private ParkingStatus status;
    private BigDecimal hourlyRate;
    private String vehicleTypeAllowed;
}