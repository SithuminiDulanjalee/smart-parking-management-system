package lk.ijse.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {

    private Long bookingId;
    private Long userId;
    private Long vehicleId;
    private Long parkingSpaceId;
    private String slotNumber;
    private String status;
    private BigDecimal totalCost;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}