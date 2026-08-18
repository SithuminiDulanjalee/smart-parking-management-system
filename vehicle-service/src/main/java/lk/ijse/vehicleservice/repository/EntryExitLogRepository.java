package lk.ijse.vehicleservice.repository;

import lk.ijse.vehicleservice.entity.EntryExitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryExitLogRepository extends JpaRepository<EntryExitLog, Long> {

    Optional<EntryExitLog> findByVehicleIdAndLogStatus(Long vehicleId, String logStatus);

    List<EntryExitLog> findByVehicleIdOrderByEntryTimeDesc(Long vehicleId);
}