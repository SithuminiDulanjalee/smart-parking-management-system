package lk.ijse.vehicleservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EntryExitLogDTO {

    private Long logId;
    private Long vehicleId;
    private String licensePlate;
    private String slotNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String logStatus;
}