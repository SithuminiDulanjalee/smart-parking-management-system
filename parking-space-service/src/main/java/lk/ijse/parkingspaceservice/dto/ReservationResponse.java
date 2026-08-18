package lk.ijse.parkingspaceservice.dto;

import lk.ijse.parkingspaceservice.entity.SpaceStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationResponse {

    private Long spaceId;
    private String slotNumber;
    private String location;
    private SpaceStatus status;
    private Long userId;
    private String message;
    private LocalDateTime timestamp;
}