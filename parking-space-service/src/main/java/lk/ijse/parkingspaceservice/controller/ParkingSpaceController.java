package lk.ijse.parkingspaceservice.controller;

import jakarta.validation.Valid;
import lk.ijse.parkingspaceservice.dto.*;
import lk.ijse.parkingspaceservice.entity.SpaceStatus;
import lk.ijse.parkingspaceservice.service.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/parking", "/api/v1/parking-spaces"})
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @PostMapping
    public ResponseEntity<ParkingSpaceResponse> createSpace(@Valid @RequestBody ParkingSpaceRequest request) {
        return new ResponseEntity<>(parkingSpaceService.createSpace(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpaceResponse> updateSpace(@PathVariable Long id,
                                                            @Valid @RequestBody ParkingSpaceRequest request) {
        return ResponseEntity.ok(parkingSpaceService.updateSpace(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpaceResponse> getSpaceById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.getSpaceById(id));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpaceResponse>> filterOrGetAllSpaces(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) SpaceStatus status) {
        return ResponseEntity.ok(parkingSpaceService.filterSpaces(location, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        parkingSpaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reserve")
    public ResponseEntity<ReservationResponse> reserveSpace(@PathVariable Long id,
                                                            @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(parkingSpaceService.reserveSpace(id, request));
    }

    @PostMapping("/{id}/release")
    public ResponseEntity<ReservationResponse> releaseSpace(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.releaseSpace(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ParkingSpaceResponse> updateSpaceStatus(@PathVariable Long id,
                                                                  @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(parkingSpaceService.updateSpaceStatus(id, request));
    }
}