package org.miniproject.restuarant.repository;

import org.miniproject.restuarant.model.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, String> {


    @Query("SELECT s FROM Salary s WHERE s.staff.ID = :staffId")
    Page<Salary> findByStaffId(@Param("staffId") String staffId, Pageable pageable);

    @Query("SELECT s FROM Salary s WHERE s.paymentDate BETWEEN :startDate AND :endDate")
    Page<Salary> findByPaymentDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

    @Query("SELECT s FROM Salary s WHERE s.isPaid = :isPaid")
    Page<Salary> findByPaymentStatus(@Param("isPaid") boolean isPaid, Pageable pageable);

    @Query("SELECT s FROM Salary s WHERE s.staff.ID = :staffId AND s.isPaid = true")
    List<Salary> findPaidSalariesByStaffId(@Param("staffId") String staffId);

    @Query("SELECT s FROM Salary s WHERE s.staff.ID = :staffId AND s.paymentDate BETWEEN :startDate AND :endDate")
    List<Salary> findByStaffIdAndPaymentDateBetween(
            @Param("staffId") String staffId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
