package lk.ijse.vehicleservice.service;

import lk.ijse.vehicleservice.dto.*;

import java.util.List;

public interface VehicleService {

    VehicleResponse registerVehicle(VehicleRequest request);

    VehicleResponse updateVehicle(Long id, VehicleRequest request);

    VehicleResponse getVehicleById(Long id);

    List<VehicleResponse> getVehiclesByUserId(Long userId);

    EntryExitLogDTO simulateEntry(EntryRequest request);

    EntryExitLogDTO simulateExit(ExitRequest request);

    List<EntryExitLogDTO> getVehicleLogs(Long vehicleId);
}