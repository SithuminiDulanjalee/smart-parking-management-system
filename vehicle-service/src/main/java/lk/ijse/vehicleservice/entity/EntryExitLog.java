package lk.ijse.vehicleservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entry_exit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryExitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private String slotNumber;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    @Column(nullable = false)
    private String logStatus; // "ACTIVE" or "COMPLETED"
}