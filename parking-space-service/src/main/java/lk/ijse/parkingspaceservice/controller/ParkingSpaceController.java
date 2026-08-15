package lk.ijse.parkingspaceservice.controller;

import jakarta.validation.Valid;
import lk.ijse.parkingspaceservice.dto.ParkingSpaceRequest;
import lk.ijse.parkingspaceservice.dto.ParkingSpaceResponse;
import lk.ijse.parkingspaceservice.dto.StatusUpdateRequest;
import lk.ijse.parkingspaceservice.entity.ParkingStatus;
import lk.ijse.parkingspaceservice.service.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-spaces")
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @PostMapping
    public ResponseEntity<ParkingSpaceResponse> createParkingSpace(@Valid @RequestBody ParkingSpaceRequest request) {
        return new ResponseEntity<>(parkingSpaceService.createParkingSpace(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpaceResponse> getParkingSpaceById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.getParkingSpaceById(id));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpaceResponse>> getAllParkingSpaces() {
        return ResponseEntity.ok(parkingSpaceService.getAllParkingSpaces());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpaceResponse> updateParkingSpace(@PathVariable Long id,
                                                                  @Valid @RequestBody ParkingSpaceRequest request) {
        return ResponseEntity.ok(parkingSpaceService.updateParkingSpace(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Long id) {
        parkingSpaceService.deleteParkingSpace(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reserve")
    public ResponseEntity<ParkingSpaceResponse> reserveSpace(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.reserveSpace(id));
    }

    @PatchMapping("/{id}/release")
    public ResponseEntity<ParkingSpaceResponse> releaseSpace(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.releaseSpace(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ParkingSpaceResponse> updateStatus(@PathVariable Long id,
                                                             @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(parkingSpaceService.updateStatus(id, request.getStatus()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ParkingSpaceResponse>> filterSpaces(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) ParkingStatus status) {
        return ResponseEntity.ok(parkingSpaceService.filterSpaces(location, status));
    }
}