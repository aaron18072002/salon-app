package com.coding.dto.request;

import com.coding.model.UserRole;
import jakarta.validation.constraints.*;

public record CreateUserRequest (

        @NotBlank(message = "Full name cannot be blank")
        @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
        String fullName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
        String phoneNumber,

        @NotBlank(message = "Password cannot be blank")
        @Size(max = 255, message = "Password must not exceed 255 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, containing at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String password,

        @NotNull(message = "Role is required")
        UserRole role

) {}
