package com.coding.mapper;

import com.coding.dto.request.CreateServiceOfferingRequest;
import com.coding.dto.request.UpdateServiceOfferingRequest;
import com.coding.dto.response.ServiceOfferingResponse;
import com.coding.model.ServiceOffering;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceOfferingMapper {

    @Mapping(target = "id", ignore = true)
    ServiceOffering toEntity(CreateServiceOfferingRequest request);

    ServiceOfferingResponse toResponse(ServiceOffering serviceOffering);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "salonId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateServiceOfferingRequest request, @MappingTarget ServiceOffering serviceOffering);

}
