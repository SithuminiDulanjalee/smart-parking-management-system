package lk.ijse.vehicleservice.controller;

import jakarta.validation.Valid;
import lk.ijse.vehicleservice.dto.VehicleRequest;
import lk.ijse.vehicleservice.dto.VehicleResponse;
import lk.ijse.vehicleservice.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(
            VehicleService vehicleService) {

        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> register(
            @Valid @RequestBody VehicleRequest request) {

        VehicleResponse response =
                vehicleService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>>
    getAll() {

        return ResponseEntity.ok(
                vehicleService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse>
    getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                vehicleService.getById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VehicleResponse>>
    getByUserId(@PathVariable Long userId) {

        return ResponseEntity.ok(
                vehicleService.getByUserId(userId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse>
    update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequest request) {

        return ResponseEntity.ok(
                vehicleService.update(
                        id,
                        request
                )
        );
    }

    @PostMapping("/{id}/entry")
    public ResponseEntity<VehicleResponse>
    entry(@PathVariable Long id) {

        return ResponseEntity.ok(
                vehicleService.entry(id)
        );
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<VehicleResponse>
    exit(@PathVariable Long id) {

        return ResponseEntity.ok(
                vehicleService.exit(id)
        );
    }
}