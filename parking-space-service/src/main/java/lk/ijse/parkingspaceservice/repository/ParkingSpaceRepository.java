package lk.ijse.parkingspaceservice.repository;

import lk.ijse.parkingspaceservice.entity.ParkingSpace;
import lk.ijse.parkingspaceservice.entity.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    boolean existsBySlotNumber(String slotNumber);

    List<ParkingSpace> findByLocationContainingIgnoreCase(String location);

    List<ParkingSpace> findByStatus(ParkingStatus status);

    List<ParkingSpace> findByLocationContainingIgnoreCaseAndStatus(String location, ParkingStatus status);
}