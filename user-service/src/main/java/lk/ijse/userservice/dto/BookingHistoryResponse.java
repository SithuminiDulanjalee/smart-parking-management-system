package lk.ijse.userservice.dto;

import lk.ijse.userservice.entity.BookingHistory;

import java.time.LocalDateTime;

public class BookingHistoryResponse {

    private Long id;
    private Long userId;
    private Long bookingId;
    private Long parkingSpaceId;
    private Long vehicleId;
    private String status;
    private LocalDateTime bookingDate;
    private LocalDateTime completedDate;
    private Double amount;

    public BookingHistoryResponse() {
    }

    public BookingHistoryResponse(
            Long id,
            Long userId,
            Long bookingId,
            Long parkingSpaceId,
            Long vehicleId,
            String status,
            LocalDateTime bookingDate,
            LocalDateTime completedDate,
            Double amount
    ) {
        this.id = id;
        this.userId = userId;
        this.bookingId = bookingId;
        this.parkingSpaceId = parkingSpaceId;
        this.vehicleId = vehicleId;
        this.status = status;
        this.bookingDate = bookingDate;
        this.completedDate = completedDate;
        this.amount = amount;
    }

    public static BookingHistoryResponse from(BookingHistory history) {

        return new BookingHistoryResponse(
                history.getId(),
                history.getUserId(),
                history.getBookingId(),
                history.getParkingSpaceId(),
                history.getVehicleId(),
                history.getStatus(),
                history.getBookingDate(),
                history.getCompletedDate(),
                history.getAmount()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(Long parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}