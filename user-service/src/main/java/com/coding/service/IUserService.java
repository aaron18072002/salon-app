package com.coding.service;

import com.coding.dto.request.CreateUserRequest;
import com.coding.dto.request.UpdateUserRequest;
import com.coding.dto.response.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    List<UserResponse> getUsersByRole(String role);

    UserResponse updateUserById(Long id, UpdateUserRequest request);

    void deleteUserById(Long id);

}
