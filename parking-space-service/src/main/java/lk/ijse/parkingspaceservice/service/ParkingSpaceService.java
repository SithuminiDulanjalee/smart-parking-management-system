package lk.ijse.parkingspaceservice.service;

import lk.ijse.parkingspaceservice.dto.*;
import lk.ijse.parkingspaceservice.entity.SpaceStatus;

import java.util.List;

public interface ParkingSpaceService {

    ParkingSpaceResponse createSpace(ParkingSpaceRequest request);

    ParkingSpaceResponse updateSpace(Long id, ParkingSpaceRequest request);

    ParkingSpaceResponse getSpaceById(Long id);

    List<ParkingSpaceResponse> getAllSpaces();

    void deleteSpace(Long id);

    List<ParkingSpaceResponse> filterSpaces(String location, SpaceStatus status);

    ReservationResponse reserveSpace(Long id, ReservationRequest request);

    ReservationResponse releaseSpace(Long id);

    ParkingSpaceResponse updateSpaceStatus(Long id, StatusUpdateRequest request);
}