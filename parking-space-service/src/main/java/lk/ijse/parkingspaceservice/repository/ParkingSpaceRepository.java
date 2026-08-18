package lk.ijse.parkingspaceservice.repository;

import lk.ijse.parkingspaceservice.entity.ParkingSpace;
import lk.ijse.parkingspaceservice.entity.SpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long>, JpaSpecificationExecutor<ParkingSpace> {

    boolean existsBySlotNumber(String slotNumber);

    Optional<ParkingSpace> findBySlotNumber(String slotNumber);

    List<ParkingSpace> findByLocationIgnoreCaseAndStatus(String location, SpaceStatus status);

    List<ParkingSpace> findByStatus(SpaceStatus status);

    List<ParkingSpace> findByLocationIgnoreCase(String location);
}