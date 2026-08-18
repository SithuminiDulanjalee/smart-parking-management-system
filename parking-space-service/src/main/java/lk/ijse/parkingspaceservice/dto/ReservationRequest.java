package lk.ijse.parkingspaceservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {

    @NotNull(message = "User ID is required")
    private Long userId;
}