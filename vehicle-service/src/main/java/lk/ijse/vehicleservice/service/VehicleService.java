package lk.ijse.vehicleservice.service;

import lk.ijse.vehicleservice.dto.VehicleRequest;
import lk.ijse.vehicleservice.dto.VehicleResponse;
import lk.ijse.vehicleservice.entity.Vehicle;
import lk.ijse.vehicleservice.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(
            VehicleRepository repository) {

        this.repository = repository;
    }

    public VehicleResponse register(
            VehicleRequest request) {

        String registrationNumber =
                request.getRegistrationNumber()
                        .trim()
                        .toUpperCase();

        if (repository.existsByRegistrationNumber(
                registrationNumber)) {

            throw new IllegalArgumentException(
                    "Vehicle registration number already exists"
            );
        }

        Vehicle vehicle = new Vehicle();

        vehicle.setRegistrationNumber(
                registrationNumber
        );

        vehicle.setVehicleType(
                request.getVehicleType()
                        .trim()
                        .toUpperCase()
        );

        vehicle.setUserId(
                request.getUserId()
        );

        vehicle.setStatus("OUTSIDE");

        Vehicle saved =
                repository.save(vehicle);

        return VehicleResponse.from(saved);
    }

    public List<VehicleResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(VehicleResponse::from)
                .toList();
    }

    public VehicleResponse getById(Long id) {

        Vehicle vehicle =
                repository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Vehicle not found"
                                ));

        return VehicleResponse.from(vehicle);
    }

    public List<VehicleResponse> getByUserId(
            Long userId) {

        return repository.findByUserId(userId)
                .stream()
                .map(VehicleResponse::from)
                .toList();
    }

    public VehicleResponse update(
            Long id,
            VehicleRequest request) {

        Vehicle vehicle =
                repository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Vehicle not found"
                                ));

        String registrationNumber =
                request.getRegistrationNumber()
                        .trim()
                        .toUpperCase();

        if (repository
                .existsByRegistrationNumberAndIdNot(
                        registrationNumber,
                        id)) {

            throw new IllegalArgumentException(
                    "Vehicle registration number already exists"
            );
        }

        vehicle.setRegistrationNumber(
                registrationNumber
        );

        vehicle.setVehicleType(
                request.getVehicleType()
                        .trim()
                        .toUpperCase()
        );

        vehicle.setUserId(
                request.getUserId()
        );

        Vehicle updated =
                repository.save(vehicle);

        return VehicleResponse.from(updated);
    }

    public VehicleResponse entry(Long id) {

        Vehicle vehicle =
                repository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Vehicle not found"
                                ));

        if ("PARKED".equalsIgnoreCase(
                vehicle.getStatus())) {

            throw new IllegalArgumentException(
                    "Vehicle is already inside"
            );
        }

        vehicle.setStatus("PARKED");

        return VehicleResponse.from(
                repository.save(vehicle)
        );
    }

    public VehicleResponse exit(Long id) {

        Vehicle vehicle =
                repository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Vehicle not found"
                                ));

        if ("OUTSIDE".equalsIgnoreCase(
                vehicle.getStatus())) {

            throw new IllegalArgumentException(
                    "Vehicle is already outside"
            );
        }

        vehicle.setStatus("OUTSIDE");

        return VehicleResponse.from(
                repository.save(vehicle)
        );
    }
}