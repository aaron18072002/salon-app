package com.coding.repository;

import com.coding.model.User;
import com.coding.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM users WHERE role = :role", nativeQuery = true)
    List<User> findAllUserByRole(@Param("role") String role);

}
