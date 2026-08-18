package lk.ijse.parkingspaceservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lk.ijse.parkingspaceservice.entity.VehicleType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParkingSpaceRequest {

    @NotBlank(message = "Slot number is required")
    private String slotNumber;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Hourly rate is required")
    @Positive(message = "Hourly rate must be greater than zero")
    private BigDecimal hourlyRate;

    @NotNull(message = "Vehicle type allowed is required")
    private VehicleType vehicleTypeAllowed;
}