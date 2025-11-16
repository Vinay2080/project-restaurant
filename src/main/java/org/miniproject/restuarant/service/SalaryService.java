package org.miniproject.restuarant.service;

import org.miniproject.restuarant.dto.salary.request.CreateSalaryRequest;
import org.miniproject.restuarant.dto.salary.response.SalaryResponse;
import org.miniproject.restuarant.model.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SalaryService {

    // Create a new salary record
    SalaryResponse createSalary(CreateSalaryRequest request);

    // Get salary by ID
    SalaryResponse getSalaryById(String salaryId);

    // Get all salaries for a specific staff member
    Page<SalaryResponse> getSalariesByStaffId(String staffId, Pageable pageable);

    // Get all salaries within a date range
    Page<SalaryResponse> getSalariesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Get salaries by payment status (paid/unpaid)
    Page<SalaryResponse> getSalariesByPaymentStatus(boolean isPaid, Pageable pageable);

    // Mark a salary as paid
    SalaryResponse markAsPaid(String salaryId);

    // Update salary details
    SalaryResponse updateSalary(String salaryId, Salary updatedSalary);

    // Delete a salary record
    void deleteSalary(String salaryId);

    // Generate monthly salary for all staff
    void generateMonthlySalaries(LocalDate paymentDate, String notes);

    // Get salary statistics for a staff member
    SalaryStatistics getSalaryStatistics(String staffId);

    // Get payroll summary for a specific period
    PayrollSummary getPayrollSummary(LocalDate startDate, LocalDate endDate);

    // DTO for salary statistics
    interface SalaryStatistics {
        BigDecimal getTotalPaid();

        BigDecimal getAverageSalary();

        int getPaymentCount();

        LocalDate getLastPaymentDate();
    }

    // DTO for payroll summary
    interface PayrollSummary {
        int getTotalStaff();

        BigDecimal getTotalPaid();

        BigDecimal getTotalTax();

        BigDecimal getTotalBonus();

        BigDecimal getTotalDeductions();

        List<SalaryResponse> getTopEarners(int limit);
    }
}
