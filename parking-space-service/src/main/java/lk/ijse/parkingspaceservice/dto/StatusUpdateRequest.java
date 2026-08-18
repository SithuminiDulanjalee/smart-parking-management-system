package lk.ijse.parkingspaceservice.dto;

import jakarta.validation.constraints.NotNull;
import lk.ijse.parkingspaceservice.entity.SpaceStatus;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private SpaceStatus status;
}