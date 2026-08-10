package com.coding.mapper;

import com.coding.dto.request.CreateSalonRequest;
import com.coding.dto.request.UpdateSalonRequest;
import com.coding.dto.response.SalonResponse;
import com.coding.model.Salon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SalonMapper {

    Salon toEntity(CreateSalonRequest request);

    SalonResponse toResponse(Salon salon);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateSalonFromRequest(UpdateSalonRequest request, @MappingTarget Salon salon);

}
