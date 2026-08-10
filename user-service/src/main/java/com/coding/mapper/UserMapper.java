package com.coding.mapper;

import com.coding.dto.request.CreateUserRequest;
import com.coding.dto.request.UpdateUserRequest;
import com.coding.dto.response.UserResponse;
import com.coding.model.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // Suppresses warnings for fields not mapped (like ID on create)
)
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(CreateUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // Passwords usually have a separate update endpoint
    @Mapping(target = "role", ignore = true)     // Roles are usually updated by an Admin endpoint
    @Mapping(target = "email", ignore = true)       // SECURITY: Ignore email updates here
    @Mapping(target = "phoneNumber", ignore = true) // SECURITY: Ignore phone updates here
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateUserRequest request, @MappingTarget User user);

}
