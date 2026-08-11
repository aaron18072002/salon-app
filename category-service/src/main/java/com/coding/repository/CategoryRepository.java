package com.coding.repository;

import com.coding.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds all categories belonging to a
     *   specific salon.
     *
     * @param salonId the unique identifier of the salon
     * @return a list of categories
     *   associated with the given salon ID
     */
    List<Category> findBySalonId(UUID salonId);

}
