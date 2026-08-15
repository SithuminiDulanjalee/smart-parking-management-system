package lk.ijse.paymentservice.service;

import lk.ijse.paymentservice.dto.PaymentRequest;
import lk.ijse.paymentservice.dto.ReceiptResponse;

public interface PaymentService {

    ReceiptResponse processPayment(PaymentRequest request);

    ReceiptResponse getReceiptById(Long id);
}