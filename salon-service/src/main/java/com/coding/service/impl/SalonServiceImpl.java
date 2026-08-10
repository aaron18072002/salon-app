package com.coding.service.impl;

import com.coding.dto.request.CreateSalonRequest;
import com.coding.dto.request.UpdateSalonRequest;
import com.coding.dto.response.SalonResponse;
import com.coding.exception.DuplicateResourceException;
import com.coding.exception.ResourceNotFoundException;
import com.coding.mapper.SalonMapper;
import com.coding.model.Salon;
import com.coding.repository.SalonRepository;
import com.coding.service.ISalonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class SalonServiceImpl implements ISalonService {

    private final SalonRepository salonRepository;
    private final SalonMapper salonMapper;

    @Override
    public SalonResponse createSalon(CreateSalonRequest request) {
        if(this.salonRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A salon with email " + request.email() + " already exists.");
        }

        Salon salon =  this.salonMapper.toEntity(request);
        Salon savedSalon = this.salonRepository.saveAndFlush(salon);

        return this.salonMapper.toResponse(savedSalon);
    }

    @Override
    public SalonResponse updateSalon(UUID salonId, Long userId, UpdateSalonRequest request) {
        Salon salon =  this.salonRepository.findById(salonId)
                .orElseThrow(() -> new ResourceNotFoundException("Salon not found with ID: " + salonId));
        // Will change this logic by spring security later
        if(!salon.getOwnerId().equals(userId)) {
            throw new RuntimeException("You do not have permission to update this salon.");
        }
        if(!salon.getEmail().equals(request.email()) && this.salonRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A salon with email " + request.email() + " already exists.");
        }

        this.salonMapper.updateSalonFromRequest(request,salon);
        Salon updatedSalon = this.salonRepository.save(salon);

        return this.salonMapper.toResponse(updatedSalon);
    }

    @Override
    public List<SalonResponse> getAllSalons() {
        return this.salonRepository.findAll().stream()
                .map(this.salonMapper::toResponse)
                .toList();
    }

    @Override
    public SalonResponse getSalonById(UUID salonId) {
        return this.salonRepository.findById(salonId)
                .map(this.salonMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Salon not found with ID: " + salonId));
    }

    @Override
    public List<SalonResponse> getSalonsByOwnerId(Long ownerId) {
        return this.salonRepository.findByOwnerId(ownerId).stream()
                .map(salonMapper::toResponse)
                .toList();
    }

        @Override
        public List<SalonResponse> searchSalonsByKeyword(String keyword) {
            return this.salonRepository.searchSalons(keyword).stream()
                    .map(salonMapper::toResponse)
                    .toList();
        }

    @Override
    public void deleteSalonById(UUID salonId) {
        if (!salonRepository.existsById(salonId)) {
            throw new ResourceNotFoundException("Cannot delete. Salon not found with ID: " + salonId);
        }
        this.salonRepository.deleteById(salonId);
    }

}
