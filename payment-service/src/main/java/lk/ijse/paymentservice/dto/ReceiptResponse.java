package lk.ijse.paymentservice.dto;

import lk.ijse.paymentservice.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReceiptResponse {

    private Long paymentId;
    private String transactionId;
    private Long userId;
    private String userName;
    private Long vehicleId;
    private String licensePlate;
    private BigDecimal amount;
    private PaymentStatus status;
    private String paymentMethod;
    private LocalDateTime timestamp;
}