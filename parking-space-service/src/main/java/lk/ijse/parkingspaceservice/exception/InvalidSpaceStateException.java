package lk.ijse.parkingspaceservice.exception;

public class InvalidSpaceStateException extends RuntimeException {
    public InvalidSpaceStateException(String message) {
        super(message);
    }
}