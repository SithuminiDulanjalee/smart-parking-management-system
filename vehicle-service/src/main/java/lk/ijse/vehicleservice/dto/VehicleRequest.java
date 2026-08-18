package lk.ijse.vehicleservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lk.ijse.vehicleservice.entity.VehicleType;
import lombok.Data;

@Data
public class VehicleRequest {

    @NotBlank(message = "License plate is required")
    @Pattern(regexp = "^[A-Z0-9-]{4,10}$", message = "Invalid license plate format")
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    @NotNull(message = "User ID is required")
    private Long userId;
}