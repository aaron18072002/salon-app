package com.coding.repository;

import com.coding.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    List<ServiceOffering> findBySalonId(UUID salonId);

    List<ServiceOffering> findByCategoryId(Long categoryId);

    List<ServiceOffering> findBySalonIdAndCategoryId(UUID salonId, Long categoryId);

    Set<ServiceOffering> findByIdIn(Set<Long> ids);

}
