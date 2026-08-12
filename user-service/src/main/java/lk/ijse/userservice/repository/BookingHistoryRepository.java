package lk.ijse.userservice.repository;

import lk.ijse.userservice.entity.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingHistoryRepository
        extends JpaRepository<BookingHistory, Long> {

    List<BookingHistory> findByUserIdOrderByBookingDateDesc(
            Long userId
    );
}