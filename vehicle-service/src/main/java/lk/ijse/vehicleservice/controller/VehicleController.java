package lk.ijse.vehicleservice.controller;

import jakarta.validation.Valid;
import lk.ijse.vehicleservice.dto.*;
import lk.ijse.vehicleservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/vehicles", "/api/v1/vehicles"})
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> registerVehicle(@Valid @RequestBody VehicleRequest request) {
        return new ResponseEntity<>(vehicleService.registerVehicle(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable Long id,
                                                         @Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VehicleResponse>> getVehiclesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByUserId(userId));
    }

    @PostMapping("/entry")
    public ResponseEntity<EntryExitLogDTO> simulateEntry(@Valid @RequestBody EntryRequest request) {
        return ResponseEntity.ok(vehicleService.simulateEntry(request));
    }

    @PostMapping("/exit")
    public ResponseEntity<EntryExitLogDTO> simulateExit(@Valid @RequestBody ExitRequest request) {
        return ResponseEntity.ok(vehicleService.simulateExit(request));
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<EntryExitLogDTO>> getVehicleLogs(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleLogs(id));
    }
}