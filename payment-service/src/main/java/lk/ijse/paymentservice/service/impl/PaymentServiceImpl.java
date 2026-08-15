package lk.ijse.paymentservice.service.impl;

import lk.ijse.paymentservice.dto.*;
import lk.ijse.paymentservice.entity.Payment;
import lk.ijse.paymentservice.entity.PaymentStatus;
import lk.ijse.paymentservice.exception.PaymentProcessingException;
import lk.ijse.paymentservice.exception.ResourceNotFoundException;
import lk.ijse.paymentservice.repository.PaymentRepository;
import lk.ijse.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Override
    public ReceiptResponse processPayment(PaymentRequest request) {
        // Inter-service call to fetch User info using Eureka Logical Name
        String userName = "N/A";
        try {
            UserDTO user = restTemplate.getForObject("http://user-service/api/v1/users/" + request.getUserId(), UserDTO.class);
            if (user != null) {
                userName = user.getName();
            }
        } catch (Exception e) {
            // Fallback if user-service is unavailable or user doesn't exist
            userName = "Unknown User";
        }

        // Inter-service call to fetch Vehicle info using Eureka Logical Name
        String licensePlate = "N/A";
        try {
            VehicleDTO vehicle = restTemplate.getForObject("http://vehicle-service/api/v1/vehicles/" + request.getVehicleId(), VehicleDTO.class);
            if (vehicle != null) {
                licensePlate = vehicle.getLicensePlate();
            }
        } catch (Exception e) {
            // Fallback if vehicle-service is unavailable
            licensePlate = "Unknown Vehicle";
        }

        // Card validation logic
        boolean isSuccess = validateMockCard(request.getCardNumber());
        if (!isSuccess) {
            throw new PaymentProcessingException("Payment failed: Invalid card details or insufficient funds.");
        }

        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String maskedCard = "XXXX-XXXX-XXXX-" + request.getCardNumber().substring(12);

        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .userId(request.getUserId())
                .vehicleId(request.getVehicleId())
                .amount(request.getAmount())
                .status(PaymentStatus.SUCCESS)
                .maskedCardNumber(maskedCard)
                .timestamp(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return ReceiptResponse.builder()
                .paymentId(savedPayment.getId())
                .transactionId(savedPayment.getTransactionId())
                .userId(savedPayment.getUserId())
                .userName(userName)
                .vehicleId(savedPayment.getVehicleId())
                .licensePlate(licensePlate)
                .amount(savedPayment.getAmount())
                .status(savedPayment.getStatus())
                .paymentMethod(savedPayment.getMaskedCardNumber())
                .timestamp(savedPayment.getTimestamp())
                .build();
    }

    @Override
    public ReceiptResponse getReceiptById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt not found for Payment ID: " + id));

        String userName = "N/A";
        try {
            UserDTO user = restTemplate.getForObject("http://user-service/api/v1/users/" + payment.getUserId(), UserDTO.class);
            if (user != null) userName = user.getName();
        } catch (Exception ignored) {}

        String licensePlate = "N/A";
        try {
            VehicleDTO vehicle = restTemplate.getForObject("http://vehicle-service/api/v1/vehicles/" + payment.getVehicleId(), VehicleDTO.class);
            if (vehicle != null) licensePlate = vehicle.getLicensePlate();
        } catch (Exception ignored) {}

        return ReceiptResponse.builder()
                .paymentId(payment.getId())
                .transactionId(payment.getTransactionId())
                .userId(payment.getUserId())
                .userName(userName)
                .vehicleId(payment.getVehicleId())
                .licensePlate(licensePlate)
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentMethod(payment.getMaskedCardNumber())
                .timestamp(payment.getTimestamp())
                .build();
    }

    private boolean validateMockCard(String cardNumber) {
        // If card number ends with 0000, failure
        return !cardNumber.endsWith("0000");
    }
}