package com.coding.repository;

import com.coding.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SalonRepository extends JpaRepository<Salon, UUID> {

    boolean existsByEmail(String email);

    List<Salon> findByOwnerId(Long id);

    List<Salon> findByCity(String city);

    @Query(
            "SELECT s FROM Salon s WHERE " +
            "LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Salon> searchSalons(@Param("keyword") String keyword);

}
