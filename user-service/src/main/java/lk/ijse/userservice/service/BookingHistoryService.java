package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.BookingHistoryRequest;
import lk.ijse.userservice.dto.BookingHistoryResponse;
import lk.ijse.userservice.entity.BookingHistory;
import lk.ijse.userservice.repository.BookingHistoryRepository;
import lk.ijse.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingHistoryService {

    private final BookingHistoryRepository historyRepository;
    private final UserRepository userRepository;

    public BookingHistoryService(
            BookingHistoryRepository historyRepository,
            UserRepository userRepository) {

        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    public BookingHistoryResponse addBookingHistory(
            Long userId,
            BookingHistoryRequest request) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException(
                    "User not found"
            );
        }

        BookingHistory history =
                new BookingHistory();

        history.setUserId(userId);

        history.setBookingId(
                request.getBookingId()
        );

        history.setParkingSpaceId(
                request.getParkingSpaceId()
        );

        history.setVehicleId(
                request.getVehicleId()
        );

        history.setStatus(
                request.getStatus()
                        .trim()
                        .toUpperCase()
        );

        history.setBookingDate(
                request.getBookingDate() != null
                        ? request.getBookingDate()
                        : LocalDateTime.now()
        );

        history.setCompletedDate(
                request.getCompletedDate()
        );

        history.setAmount(
                request.getAmount()
        );

        BookingHistory saved =
                historyRepository.save(history);

        return BookingHistoryResponse.from(saved);
    }

    public List<BookingHistoryResponse> getUserHistory(
            Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException(
                    "User not found"
            );
        }

        return historyRepository
                .findByUserIdOrderByBookingDateDesc(userId)
                .stream()
                .map(BookingHistoryResponse::from)
                .toList();
    }
}