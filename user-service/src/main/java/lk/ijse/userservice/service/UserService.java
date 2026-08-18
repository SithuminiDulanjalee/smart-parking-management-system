package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.*;

import java.util.List;

public interface UserService {

    UserProfileResponse registerUser(UserRegisterRequest request);

    AuthResponse authenticateUser(AuthRequest request);

    UserProfileResponse getUserProfile(Long id);

    UserProfileResponse updateUserProfile(Long id, UserUpdateRequest request);

    List<BookingDTO> getUserBookingHistory(Long id);
}