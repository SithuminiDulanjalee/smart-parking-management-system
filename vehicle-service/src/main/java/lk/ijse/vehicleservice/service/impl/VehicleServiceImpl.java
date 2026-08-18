package lk.ijse.vehicleservice.service.impl;

import lk.ijse.vehicleservice.dto.*;
import lk.ijse.vehicleservice.entity.EntryExitLog;
import lk.ijse.vehicleservice.entity.Vehicle;
import lk.ijse.vehicleservice.entity.VehicleStatus;
import lk.ijse.vehicleservice.exception.InvalidVehicleStateException;
import lk.ijse.vehicleservice.exception.ResourceAlreadyExistsException;
import lk.ijse.vehicleservice.exception.ResourceNotFoundException;
import lk.ijse.vehicleservice.repository.EntryExitLogRepository;
import lk.ijse.vehicleservice.repository.VehicleRepository;
import lk.ijse.vehicleservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final EntryExitLogRepository entryExitLogRepository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public VehicleResponse registerVehicle(VehicleRequest request) {
        if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new ResourceAlreadyExistsException("Vehicle already registered with license plate: " + request.getLicensePlate());
        }

        UserDTO user = fetchUser(request.getUserId());

        Vehicle vehicle = Vehicle.builder()
                .licensePlate(request.getLicensePlate().toUpperCase())
                .vehicleType(request.getVehicleType())
                .userId(request.getUserId())
                .status(VehicleStatus.OUT)
                .createdAt(LocalDateTime.now())
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapToVehicleResponse(savedVehicle, user);
    }

    @Override
    @Transactional
    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));

        if (!vehicle.getLicensePlate().equalsIgnoreCase(request.getLicensePlate()) &&
                vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new ResourceAlreadyExistsException("License plate already in use: " + request.getLicensePlate());
        }

        UserDTO user = fetchUser(request.getUserId());

        vehicle.setLicensePlate(request.getLicensePlate().toUpperCase());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setUserId(request.getUserId());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToVehicleResponse(updatedVehicle, user);
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));
        UserDTO user = fetchUser(vehicle.getUserId());
        return mapToVehicleResponse(vehicle, user);
    }

    @Override
    public List<VehicleResponse> getVehiclesByUserId(Long userId) {
        UserDTO user = fetchUser(userId);
        return vehicleRepository.findByUserId(userId).stream()
                .map(v -> mapToVehicleResponse(v, user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EntryExitLogDTO simulateEntry(EntryRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + request.getVehicleId()));

        if (vehicle.getStatus() == VehicleStatus.PARKED) {
            throw new InvalidVehicleStateException("Vehicle is already inside the parking facility.");
        }

        vehicle.setStatus(VehicleStatus.PARKED);
        vehicleRepository.save(vehicle);

        EntryExitLog log = EntryExitLog.builder()
                .vehicle(vehicle)
                .slotNumber(request.getSlotNumber())
                .entryTime(LocalDateTime.now())
                .logStatus("ACTIVE")
                .build();

        EntryExitLog savedLog = entryExitLogRepository.save(log);
        return mapToLogDTO(savedLog);
    }

    @Override
    @Transactional
    public EntryExitLogDTO simulateExit(ExitRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + request.getVehicleId()));

        if (vehicle.getStatus() == VehicleStatus.OUT) {
            throw new InvalidVehicleStateException("Vehicle is not inside the parking facility.");
        }

        EntryExitLog activeLog = entryExitLogRepository.findByVehicleIdAndLogStatus(request.getVehicleId(), "ACTIVE")
                .orElseThrow(() -> new ResourceNotFoundException("Active entry record not found for vehicle ID: " + request.getVehicleId()));

        activeLog.setExitTime(LocalDateTime.now());
        activeLog.setLogStatus("COMPLETED");

        vehicle.setStatus(VehicleStatus.OUT);
        vehicleRepository.save(vehicle);

        EntryExitLog updatedLog = entryExitLogRepository.save(activeLog);
        return mapToLogDTO(updatedLog);
    }

    @Override
    public List<EntryExitLogDTO> getVehicleLogs(Long vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new ResourceNotFoundException("Vehicle not found with ID: " + vehicleId);
        }
        return entryExitLogRepository.findByVehicleIdOrderByEntryTimeDesc(vehicleId).stream()
                .map(this::mapToLogDTO)
                .collect(Collectors.toList());
    }

    private UserDTO fetchUser(Long userId) {
        try {
            return restTemplate.getForObject("http://user-service/api/v1/users/" + userId, UserDTO.class);
        } catch (Exception e) {
            UserDTO fallbackUser = new UserDTO();
            fallbackUser.setId(userId);
            fallbackUser.setName("User Service Unavailable");
            fallbackUser.setEmail("N/A");
            return fallbackUser;
        }
    }

    private VehicleResponse mapToVehicleResponse(Vehicle vehicle, UserDTO user) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .userId(vehicle.getUserId())
                .ownerName(user != null ? user.getName() : "Unknown")
                .ownerEmail(user != null ? user.getEmail() : "Unknown")
                .status(vehicle.getStatus())
                .createdAt(vehicle.getCreatedAt())
                .build();
    }

    private EntryExitLogDTO mapToLogDTO(EntryExitLog log) {
        return EntryExitLogDTO.builder()
                .logId(log.getId())
                .vehicleId(log.getVehicle().getId())
                .licensePlate(log.getVehicle().getLicensePlate())
                .slotNumber(log.getSlotNumber())
                .entryTime(log.getEntryTime())
                .exitTime(log.getExitTime())
                .logStatus(log.getLogStatus())
                .build();
    }
}