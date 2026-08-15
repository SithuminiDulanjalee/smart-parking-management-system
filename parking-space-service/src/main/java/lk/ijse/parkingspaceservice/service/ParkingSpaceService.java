package lk.ijse.parkingspaceservice.service;

import lk.ijse.parkingspaceservice.dto.ParkingSpaceRequest;
import lk.ijse.parkingspaceservice.dto.ParkingSpaceResponse;
import lk.ijse.parkingspaceservice.entity.ParkingStatus;

import java.util.List;

public interface ParkingSpaceService {

    ParkingSpaceResponse createParkingSpace(ParkingSpaceRequest request);

    ParkingSpaceResponse getParkingSpaceById(Long id);

    List<ParkingSpaceResponse> getAllParkingSpaces();

    ParkingSpaceResponse updateParkingSpace(Long id, ParkingSpaceRequest request);

    void deleteParkingSpace(Long id);

    ParkingSpaceResponse reserveSpace(Long id);

    ParkingSpaceResponse releaseSpace(Long id);

    ParkingSpaceResponse updateStatus(Long id, ParkingStatus status);

    List<ParkingSpaceResponse> filterSpaces(String location, ParkingStatus status);
}