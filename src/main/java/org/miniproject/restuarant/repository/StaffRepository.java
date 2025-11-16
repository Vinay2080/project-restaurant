package org.miniproject.restuarant.repository;

import org.miniproject.restuarant.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {


    boolean existsByID(String employeeId);

    @Query("SELECT s FROM Staff s WHERE s.user.ID = :userID")
    Optional<Staff> findByUserId(@Param("userID") String userID);

    @Query("SELECT s FROM Staff s WHERE s.isActive = :isActive")
    Page<Staff> findByActiveStatus(@Param("isActive") boolean isActive, Pageable pageable);

    @Query("SELECT s FROM Staff s WHERE s.position = :position AND s.isActive = true")
    Page<Staff> findByPosition(@Param("position") String position, Pageable pageable);

    @Query("SELECT s FROM Staff s WHERE s.employmentType = :employmentType AND s.isActive = true")
    Page<Staff> findByEmploymentType(@Param("employmentType") Staff.EmploymentType employmentType, Pageable pageable);
}
