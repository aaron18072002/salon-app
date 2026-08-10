package com.coding.service.impl;

import com.coding.dto.request.CreateUserRequest;
import com.coding.dto.request.UpdateUserRequest;
import com.coding.dto.response.UserResponse;
import com.coding.exception.DuplicateResourceException;
import com.coding.exception.ResourceNotFoundException;
import com.coding.mapper.UserMapper;
import com.coding.model.User;
import com.coding.repository.UserRepository;
import com.coding.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if(this.userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }
        if(this.userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        User user = this.userMapper.toEntity(request);
        User savedUser =  this.userRepository.save(user);

        return this.userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        return this.userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getUsersByRole(String role) {
        return this.userRepository.findAllUserByRole(role)
                .stream()
                .map(this.userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUserById(Long id, UpdateUserRequest request) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Map allowed non-null fields from the request to the existing user.
        // Sensitive fields (email, phone, password, role) are strictly ignored by the Mapper.
        this.userMapper.updateEntityFromRequest(request, user);
        User updatedUser = this.userRepository.save(user);

        return this.userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        this.userRepository.deleteById(id);
    }

}
