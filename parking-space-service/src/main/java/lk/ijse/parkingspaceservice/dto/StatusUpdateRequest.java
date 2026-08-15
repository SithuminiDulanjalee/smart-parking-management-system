package lk.ijse.parkingspaceservice.dto;

import jakarta.validation.constraints.NotNull;
import lk.ijse.parkingspaceservice.entity.ParkingStatus;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private ParkingStatus status;
}