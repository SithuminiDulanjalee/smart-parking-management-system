package lk.ijse.userservice.controller;

import jakarta.validation.Valid;
import lk.ijse.userservice.dto.LoginRequest;
import lk.ijse.userservice.dto.LoginResponse;
import lk.ijse.userservice.dto.RegisterRequest;
import lk.ijse.userservice.dto.UpdateProfileRequest;
import lk.ijse.userservice.dto.UserResponse;
import lk.ijse.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response =
                userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userService.login(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getProfile(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getProfile(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateProfileRequest request) {

        return ResponseEntity.ok(
                userService.updateProfile(
                        id,
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }
}