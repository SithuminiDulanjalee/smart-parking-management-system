package lk.ijse.paymentservice.controller;

import jakarta.validation.Valid;
import lk.ijse.paymentservice.dto.PaymentRequest;
import lk.ijse.paymentservice.dto.ReceiptResponse;
import lk.ijse.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ReceiptResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        ReceiptResponse receipt = paymentService.processPayment(request);
        return new ResponseEntity<>(receipt, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptResponse> getReceipt(@PathVariable Long id) {
        ReceiptResponse receipt = paymentService.getReceiptById(id);
        return ResponseEntity.ok(receipt);
    }
}