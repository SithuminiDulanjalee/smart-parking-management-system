package lk.ijse.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class BookingHistoryRequest {

        @NotNull
        private Long bookingId;

        private Long parkingSpaceId;

        private Long vehicleId;

        @NotBlank
        private String status;

        private LocalDateTime bookingDate;

        private LocalDateTime completedDate;

        private Double amount;

        public BookingHistoryRequest() {
        }

        public BookingHistoryRequest(
                Long bookingId,
                Long parkingSpaceId,
                Long vehicleId,
                String status,
                LocalDateTime bookingDate,
                LocalDateTime completedDate,
                Double amount
        ) {
                this.bookingId = bookingId;
                this.parkingSpaceId = parkingSpaceId;
                this.vehicleId = vehicleId;
                this.status = status;
                this.bookingDate = bookingDate;
                this.completedDate = completedDate;
                this.amount = amount;
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