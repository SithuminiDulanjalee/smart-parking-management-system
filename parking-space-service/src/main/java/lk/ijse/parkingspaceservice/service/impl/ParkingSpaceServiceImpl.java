package lk.ijse.parkingspaceservice.service.impl;

import lk.ijse.parkingspaceservice.dto.ParkingSpaceRequest;
import lk.ijse.parkingspaceservice.dto.ParkingSpaceResponse;
import lk.ijse.parkingspaceservice.entity.ParkingSpace;
import lk.ijse.parkingspaceservice.entity.ParkingStatus;
import lk.ijse.parkingspaceservice.exception.InvalidOperationException;
import lk.ijse.parkingspaceservice.exception.ResourceNotFoundException;
import lk.ijse.parkingspaceservice.repository.ParkingSpaceRepository;
import lk.ijse.parkingspaceservice.service.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public ParkingSpaceResponse createParkingSpace(ParkingSpaceRequest request) {
        if (parkingSpaceRepository.existsBySlotNumber(request.getSlotNumber())) {
            throw new InvalidOperationException("Slot number already exists: " + request.getSlotNumber());
        }

        ParkingSpace parkingSpace = ParkingSpace.builder()
                .slotNumber(request.getSlotNumber())
                .location(request.getLocation())
                .status(ParkingStatus.AVAILABLE)
                .hourlyRate(request.getHourlyRate())
                .vehicleTypeAllowed(request.getVehicleTypeAllowed())
                .build();

        ParkingSpace saved = parkingSpaceRepository.save(parkingSpace);
        return mapToResponse(saved);
    }

    @Override
    public ParkingSpaceResponse getParkingSpaceById(Long id) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));
        return mapToResponse(parkingSpace);
    }

    @Override
    public List<ParkingSpaceResponse> getAllParkingSpaces() {
        return parkingSpaceRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSpaceResponse updateParkingSpace(Long id, ParkingSpaceRequest request) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        if (!parkingSpace.getSlotNumber().equals(request.getSlotNumber()) &&
                parkingSpaceRepository.existsBySlotNumber(request.getSlotNumber())) {
            throw new InvalidOperationException("Slot number already in use: " + request.getSlotNumber());
        }

        parkingSpace.setSlotNumber(request.getSlotNumber());
        parkingSpace.setLocation(request.getLocation());
        parkingSpace.setHourlyRate(request.getHourlyRate());
        parkingSpace.setVehicleTypeAllowed(request.getVehicleTypeAllowed());

        ParkingSpace updated = parkingSpaceRepository.save(parkingSpace);
        return mapToResponse(updated);
    }

    @Override
    public void deleteParkingSpace(Long id) {
        if (!parkingSpaceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Parking space not found with ID: " + id);
        }
        parkingSpaceRepository.deleteById(id);
    }

    @Override
    public ParkingSpaceResponse reserveSpace(Long id) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        if (parkingSpace.getStatus() != ParkingStatus.AVAILABLE) {
            throw new InvalidOperationException("Space cannot be reserved. Current status: " + parkingSpace.getStatus());
        }

        parkingSpace.setStatus(ParkingStatus.RESERVED);
        ParkingSpace updated = parkingSpaceRepository.save(parkingSpace);
        return mapToResponse(updated);
    }

    @Override
    public ParkingSpaceResponse releaseSpace(Long id) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        if (parkingSpace.getStatus() == ParkingStatus.AVAILABLE) {
            throw new InvalidOperationException("Parking space is already available.");
        }

        parkingSpace.setStatus(ParkingStatus.AVAILABLE);
        ParkingSpace updated = parkingSpaceRepository.save(parkingSpace);
        return mapToResponse(updated);
    }

    @Override
    public ParkingSpaceResponse updateStatus(Long id, ParkingStatus status) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        parkingSpace.setStatus(status);
        ParkingSpace updated = parkingSpaceRepository.save(parkingSpace);
        return mapToResponse(updated);
    }

    @Override
    public List<ParkingSpaceResponse> filterSpaces(String location, ParkingStatus status) {
        List<ParkingSpace> result;

        if (location != null && status != null) {
            result = parkingSpaceRepository.findByLocationContainingIgnoreCaseAndStatus(location, status);
        } else if (location != null) {
            result = parkingSpaceRepository.findByLocationContainingIgnoreCase(location);
        } else if (status != null) {
            result = parkingSpaceRepository.findByStatus(status);
        } else {
            result = parkingSpaceRepository.findAll();
        }

        return result.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ParkingSpaceResponse mapToResponse(ParkingSpace entity) {
        return ParkingSpaceResponse.builder()
                .id(entity.getId())
                .slotNumber(entity.getSlotNumber())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .hourlyRate(entity.getHourlyRate())
                .vehicleTypeAllowed(entity.getVehicleTypeAllowed())
                .build();
    }
}