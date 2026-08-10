package com.coding.service;

import com.coding.dto.request.CreateSalonRequest;
import com.coding.dto.request.UpdateSalonRequest;
import com.coding.dto.response.SalonResponse;

import java.util.List;
import java.util.UUID;

public interface ISalonService {

    SalonResponse createSalon(CreateSalonRequest request);

    SalonResponse updateSalon(UUID salonId, Long userId, UpdateSalonRequest request);

    List<SalonResponse> getAllSalons();

    SalonResponse getSalonById(UUID salonId);

    // One owner can have multiple salon stores
    List<SalonResponse> getSalonsByOwnerId(Long ownerId);

    List<SalonResponse> searchSalonsByKeyword(String keyword);

    void deleteSalonById(UUID salonId);

}
