package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.LoginRequest;
import lk.ijse.userservice.dto.LoginResponse;
import lk.ijse.userservice.dto.RegisterRequest;
import lk.ijse.userservice.dto.UpdateProfileRequest;
import lk.ijse.userservice.dto.UserResponse;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        User user = new User();

        user.setName(
                request.getName().trim()
        );

        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(
                request.getRole()
                        .trim()
                        .toUpperCase()
        );

        User savedUser =
                userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        return new LoginResponse(
                "Login successful",
                UserResponse.from(user)
        );
    }

    public UserResponse getProfile(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        return UserResponse.from(user);
    }

    public UserResponse updateProfile(
            Long id,
            UpdateProfileRequest request) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        String newEmail = request.getEmail()
                .trim()
                .toLowerCase();

        if (!user.getEmail().equals(newEmail)
                && userRepository.existsByEmail(newEmail)) {

            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        user.setName(
                request.getName().trim()
        );

        user.setEmail(newEmail);

        User updatedUser =
                userRepository.save(user);

        return UserResponse.from(updatedUser);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }
}