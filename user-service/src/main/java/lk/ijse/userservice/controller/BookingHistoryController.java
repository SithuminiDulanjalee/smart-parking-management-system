package lk.ijse.userservice.controller;

import jakarta.validation.Valid;
import lk.ijse.userservice.dto.BookingHistoryRequest;
import lk.ijse.userservice.dto.BookingHistoryResponse;
import lk.ijse.userservice.service.BookingHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class BookingHistoryController {

    private final BookingHistoryService historyService;

    public BookingHistoryController(
            BookingHistoryService historyService) {

        this.historyService = historyService;
    }

    @PostMapping("/{userId}/booking-history")
    public ResponseEntity<BookingHistoryResponse> addHistory(
            @PathVariable Long userId,
            @Valid @RequestBody BookingHistoryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        historyService.addBookingHistory(
                                userId,
                                request
                        )
                );
    }

    @GetMapping("/{userId}/booking-history")
    public ResponseEntity<List<BookingHistoryResponse>>
    getHistory(@PathVariable Long userId) {

        return ResponseEntity.ok(
                historyService.getUserHistory(userId)
        );
    }
}