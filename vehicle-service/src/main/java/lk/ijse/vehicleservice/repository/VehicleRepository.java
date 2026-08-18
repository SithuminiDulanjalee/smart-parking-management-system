package lk.ijse.vehicleservice.repository;

import lk.ijse.vehicleservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByLicensePlate(String licensePlate);

    Optional<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> findByUserId(Long userId);
}