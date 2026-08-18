package lk.ijse.parkingspaceservice.service.impl;

import lk.ijse.parkingspaceservice.dto.*;
import lk.ijse.parkingspaceservice.entity.ParkingSpace;
import lk.ijse.parkingspaceservice.entity.SpaceStatus;
import lk.ijse.parkingspaceservice.exception.InvalidSpaceStateException;
import lk.ijse.parkingspaceservice.exception.ResourceAlreadyExistsException;
import lk.ijse.parkingspaceservice.exception.ResourceNotFoundException;
import lk.ijse.parkingspaceservice.repository.ParkingSpaceRepository;
import lk.ijse.parkingspaceservice.service.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    @Transactional
    public ParkingSpaceResponse createSpace(ParkingSpaceRequest request) {
        if (parkingSpaceRepository.existsBySlotNumber(request.getSlotNumber())) {
            throw new ResourceAlreadyExistsException("Parking slot already exists with number: " + request.getSlotNumber());
        }

        ParkingSpace space = ParkingSpace.builder()
                .slotNumber(request.getSlotNumber().toUpperCase())
                .location(request.getLocation())
                .hourlyRate(request.getHourlyRate())
                .vehicleTypeAllowed(request.getVehicleTypeAllowed())
                .status(SpaceStatus.AVAILABLE)
                .updatedAt(LocalDateTime.now())
                .build();

        ParkingSpace savedSpace = parkingSpaceRepository.save(space);
        return mapToResponse(savedSpace);
    }

    @Override
    @Transactional
    public ParkingSpaceResponse updateSpace(Long id, ParkingSpaceRequest request) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        if (!space.getSlotNumber().equalsIgnoreCase(request.getSlotNumber()) &&
                parkingSpaceRepository.existsBySlotNumber(request.getSlotNumber())) {
            throw new ResourceAlreadyExistsException("Parking slot number already exists: " + request.getSlotNumber());
        }

        space.setSlotNumber(request.getSlotNumber().toUpperCase());
        space.setLocation(request.getLocation());
        space.setHourlyRate(request.getHourlyRate());
        space.setVehicleTypeAllowed(request.getVehicleTypeAllowed());
        space.setUpdatedAt(LocalDateTime.now());

        ParkingSpace updatedSpace = parkingSpaceRepository.save(space);
        return mapToResponse(updatedSpace);
    }

    @Override
    public ParkingSpaceResponse getSpaceById(Long id) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));
        return mapToResponse(space);
    }

    @Override
    public List<ParkingSpaceResponse> getAllSpaces() {
        return parkingSpaceRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSpace(Long id) {
        if (!parkingSpaceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Parking space not found with ID: " + id);
        }
        parkingSpaceRepository.deleteById(id);
    }

    @Override
    public List<ParkingSpaceResponse> filterSpaces(String location, SpaceStatus status) {
        List<ParkingSpace> spaces;

        if (location != null && !location.isBlank() && status != null) {
            spaces = parkingSpaceRepository.findByLocationIgnoreCaseAndStatus(location, status);
        } else if (location != null && !location.isBlank()) {
            spaces = parkingSpaceRepository.findByLocationIgnoreCase(location);
        } else if (status != null) {
            spaces = parkingSpaceRepository.findByStatus(status);
        } else {
            spaces = parkingSpaceRepository.findAll();
        }

        return spaces.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationResponse reserveSpace(Long id, ReservationRequest request) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        if (space.getStatus() != SpaceStatus.AVAILABLE) {
            throw new InvalidSpaceStateException("Parking space is not available for reservation. Current status: " + space.getStatus());
        }

        space.setStatus(SpaceStatus.RESERVED);
        space.setCurrentReservedUserId(request.getUserId());
        space.setUpdatedAt(LocalDateTime.now());

        parkingSpaceRepository.save(space);

        return ReservationResponse.builder()
                .spaceId(space.getId())
                .slotNumber(space.getSlotNumber())
                .location(space.getLocation())
                .status(space.getStatus())
                .userId(request.getUserId())
                .message("Parking space reserved successfully.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public ReservationResponse releaseSpace(Long id) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        if (space.getStatus() == SpaceStatus.AVAILABLE) {
            throw new InvalidSpaceStateException("Parking space is already available.");
        }

        Long previousUserId = space.getCurrentReservedUserId();
        space.setStatus(SpaceStatus.AVAILABLE);
        space.setCurrentReservedUserId(null);
        space.setUpdatedAt(LocalDateTime.now());

        parkingSpaceRepository.save(space);

        return ReservationResponse.builder()
                .spaceId(space.getId())
                .slotNumber(space.getSlotNumber())
                .location(space.getLocation())
                .status(space.getStatus())
                .userId(previousUserId)
                .message("Parking space released successfully.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public ParkingSpaceResponse updateSpaceStatus(Long id, StatusUpdateRequest request) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        space.setStatus(request.getStatus());
        if (request.getStatus() == SpaceStatus.AVAILABLE) {
            space.setCurrentReservedUserId(null);
        }
        space.setUpdatedAt(LocalDateTime.now());

        ParkingSpace updatedSpace = parkingSpaceRepository.save(space);
        return mapToResponse(updatedSpace);
    }

    private ParkingSpaceResponse mapToResponse(ParkingSpace space) {
        return ParkingSpaceResponse.builder()
                .id(space.getId())
                .slotNumber(space.getSlotNumber())
                .location(space.getLocation())
                .hourlyRate(space.getHourlyRate())
                .vehicleTypeAllowed(space.getVehicleTypeAllowed())
                .status(space.getStatus())
                .currentReservedUserId(space.getCurrentReservedUserId())
                .updatedAt(space.getUpdatedAt())
                .build();
    }
}