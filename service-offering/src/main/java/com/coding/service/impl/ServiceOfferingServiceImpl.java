package com.coding.service.impl;

import com.coding.dto.request.CreateServiceOfferingRequest;
import com.coding.dto.request.UpdateServiceOfferingRequest;
import com.coding.dto.response.ServiceOfferingResponse;
import com.coding.exception.ResourceNotFoundException;
import com.coding.mapper.ServiceOfferingMapper;
import com.coding.model.ServiceOffering;
import com.coding.repository.ServiceOfferingRepository;
import com.coding.service.IServiceOffering;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ServiceOfferingServiceImpl implements IServiceOffering {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ServiceOfferingMapper serviceOfferingMapper;

    @Override
    public ServiceOfferingResponse createServiceOffering(CreateServiceOfferingRequest request) {
        ServiceOffering serviceOffering = this.serviceOfferingMapper.toEntity(request);
        ServiceOffering savedServiceOffering = this.serviceOfferingRepository.save(serviceOffering);
        return this.serviceOfferingMapper.toResponse(savedServiceOffering);
    }

    @Override
    public ServiceOfferingResponse updateServiceOffering(Long id, UpdateServiceOfferingRequest request) {
        ServiceOffering serviceOffering = this.serviceOfferingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service offering not found with ID: " + id));

        this.serviceOfferingMapper.updateEntityFromRequest(request, serviceOffering);
        ServiceOffering updatedServiceOffering = this.serviceOfferingRepository.saveAndFlush(serviceOffering);

        return this.serviceOfferingMapper.toResponse(updatedServiceOffering);
    }

    @Override
    public ServiceOfferingResponse getServiceOfferingById(Long id) {
        return this.serviceOfferingRepository.findById(id)
                .map(this.serviceOfferingMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Service offering not found with ID: " + id));
    }

    @Override
    public List<ServiceOfferingResponse> getAllServicesBySalonId(UUID salonId) {
        return this.serviceOfferingRepository.findBySalonId(salonId).stream()
                .map(this.serviceOfferingMapper::toResponse)
                .toList();
    }

    @Override
    public List<ServiceOfferingResponse> getServicesByCategoryId(Long categoryId) {
        return this.serviceOfferingRepository.findByCategoryId(categoryId).stream()
                .map(this.serviceOfferingMapper::toResponse)
                .toList();
    }

    @Override
    public List<ServiceOfferingResponse> getServicesBySalonIdAndCategoryId(UUID salonId, Long categoryId) {
        return this.serviceOfferingRepository.findBySalonIdAndCategoryId(salonId, categoryId).stream()
                .map(this.serviceOfferingMapper::toResponse)
                .toList();
    }

    @Override
    public Set<ServiceOfferingResponse> getServicesByIds(Set<Long> ids) {
        return this.serviceOfferingRepository.findByIdIn(ids).stream()
                .map(this.serviceOfferingMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteServiceOffering(Long id) {
        if (!this.serviceOfferingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Service offering not found with ID: " + id);
        }
        this.serviceOfferingRepository.deleteById(id);
    }

}
