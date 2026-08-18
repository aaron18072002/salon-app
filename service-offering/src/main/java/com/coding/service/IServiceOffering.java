package com.coding.service;

import com.coding.dto.request.CreateServiceOfferingRequest;
import com.coding.dto.request.UpdateServiceOfferingRequest;
import com.coding.dto.response.ServiceOfferingResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IServiceOffering {

    ServiceOfferingResponse createServiceOffering(CreateServiceOfferingRequest request);

    ServiceOfferingResponse updateServiceOffering(Long id, UpdateServiceOfferingRequest request);

    ServiceOfferingResponse getServiceOfferingById(Long id);

    List<ServiceOfferingResponse> getAllServicesBySalonId(UUID salonId);

    List<ServiceOfferingResponse> getServicesByCategoryId(Long categoryId);

    List<ServiceOfferingResponse> getServicesBySalonIdAndCategoryId(UUID salonId, Long categoryId);

    Set<ServiceOfferingResponse> getServicesByIds(Set<Long> ids);

    void deleteServiceOffering(Long id);

}
