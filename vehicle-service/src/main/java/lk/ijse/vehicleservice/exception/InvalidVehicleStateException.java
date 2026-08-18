package lk.ijse.vehicleservice.exception;

public class InvalidVehicleStateException extends RuntimeException {
    public InvalidVehicleStateException(String message) {
        super(message);
    }
}