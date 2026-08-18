package lk.ijse.vehicleservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExitRequest {

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;
}