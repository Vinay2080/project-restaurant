package org.miniproject.restuarant.serviceImplementation;

import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.salary.request.CreateSalaryRequest;
import org.miniproject.restuarant.dto.salary.response.SalaryResponse;
import org.miniproject.restuarant.exception.BusinessException;
import org.miniproject.restuarant.exception.ErrorCode;
import org.miniproject.restuarant.mapper.SalaryMapper;
import org.miniproject.restuarant.model.Salary;
import org.miniproject.restuarant.model.Staff;
import org.miniproject.restuarant.repository.SalaryRepository;
import org.miniproject.restuarant.repository.StaffRepository;
import org.miniproject.restuarant.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final StaffRepository staffRepository;
    private final SalaryMapper salaryMapper;

    private static BigDecimal getBigDecimal(Staff staff) {
        BigDecimal baseSalary;

        if (staff.getEmploymentType() == Staff.EmploymentType.FULL_TIME ||
                staff.getEmploymentType() == Staff.EmploymentType.PART_TIME) {
            baseSalary = staff.getMonthlySalary();
        } else {
            // For contract/temporary staff, you might want to implement hour tracking
            // For now; we'll use a default of 160 hours (20 days * 8 hours)
            baseSalary = staff.getHourlyRate() != null ?
                    staff.getHourlyRate().multiply(BigDecimal.valueOf(160)) : BigDecimal.ZERO;
        }
        return baseSalary;
    }

    @Override
    @Transactional
    public SalaryResponse createSalary(CreateSalaryRequest request) {
        // Find the staff member
        Staff staff = staffRepository.findById(request.getStaffID())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, request.getStaffID()));


        // Check for duplicate salary record for the same period
        List<Salary> existingSalaries = salaryRepository.findByStaffIdAndPaymentDateBetween(
                request.getStaffID(),
                request.getPaymentPeriodStart(),
                request.getPaymentPeriodEnd()
        );

        if (!existingSalaries.isEmpty()) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // Map request to entity
        Salary salary = salaryMapper.toEntity(request);
        salary.setStaff(staff);

        // Save the salary record
        Salary savedSalary = salaryRepository.save(salary);
        return salaryMapper.toResponse(savedSalary);
    }

    @Override
    @Transactional(readOnly = true)
    public SalaryResponse getSalaryById(String salaryID) {
        Salary salary = findSalaryById(salaryID);
        return salaryMapper.toResponse(salary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalaryResponse> getSalariesByStaffId(String staffId, Pageable pageable) {
        // Verify staff exists
        if (!staffRepository.existsById(staffId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Staff not found with ID: " + staffId);
        }

        return salaryRepository.findByStaffId(staffId, pageable)
                .map(salaryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalaryResponse> getSalariesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        if (startDate.isAfter(endDate)) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR);
        }

        return salaryRepository.findByPaymentDateBetween(startDate, endDate, pageable)
                .map(salaryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalaryResponse> getSalariesByPaymentStatus(boolean isPaid, Pageable pageable) {
        return salaryRepository.findByPaymentStatus(isPaid, pageable)
                .map(salaryMapper::toResponse);
    }

    @Override
    @Transactional
    public SalaryResponse markAsPaid(String salaryId) {
        Salary salary = findSalaryById(salaryId);

        if (salary.isPaid()) {
            throw new BusinessException(ErrorCode.INVALID_OPERATION);
        }

        salary.setPaid(true);
        Salary updatedSalary = salaryRepository.save(salary);
        return salaryMapper.toResponse(updatedSalary);
    }

    @Override
    @Transactional
    public SalaryResponse updateSalary(String salaryId, Salary updatedSalary) {
        Salary existingSalary = findSalaryById(salaryId);

        // Update fields that are allowed to be updated
        if (updatedSalary.getBaseSalary() != null) {
            existingSalary.setBaseSalary(updatedSalary.getBaseSalary());
        }

        if (updatedSalary.getBonusAmount() != null) {
            existingSalary.setBonusAmount(updatedSalary.getBonusAmount());
        }

        if (updatedSalary.getDeductions() != null) {
            existingSalary.setDeductions(updatedSalary.getDeductions());
        }

        if (updatedSalary.getPaymentDate() != null) {
            existingSalary.setPaymentDate(updatedSalary.getPaymentDate());
        }

        if (updatedSalary.getPaymentPeriodStart() != null) {
            existingSalary.setPaymentPeriodStart(updatedSalary.getPaymentPeriodStart());
        }

        if (updatedSalary.getPaymentPeriodEnd() != null) {
            existingSalary.setPaymentPeriodEnd(updatedSalary.getPaymentPeriodEnd());
        }

        if (updatedSalary.getPaymentMethod() != null) {
            existingSalary.setPaymentMethod(updatedSalary.getPaymentMethod());
        }

        if (updatedSalary.getNotes() != null) {
            existingSalary.setNotes(updatedSalary.getNotes());
        }

        Salary savedSalary = salaryRepository.save(existingSalary);
        return salaryMapper.toResponse(savedSalary);
    }

    @Override
    @Transactional
    public void deleteSalary(String salaryId) {
        Salary salary = findSalaryById(salaryId);
        salaryRepository.delete(salary);
    }

    @Override
    @Transactional
    public void generateMonthlySalaries(LocalDate paymentDate, String notes) {
        // Get all active staff members
        List<Staff> activeStaff = staffRepository.findByActiveStatus(true, null).getContent();

        // Calculate the start and end of the previous month
        LocalDate startOfMonth = paymentDate.withDayOfMonth(1);
        LocalDate endOfMonth = paymentDate.withDayOfMonth(paymentDate.lengthOfMonth());

        for (Staff staff : activeStaff) {
            // Skip if salary already generated for this period
            List<Salary> existingSalaries = salaryRepository.findByStaffIdAndPaymentDateBetween(
                    staff.getID(), startOfMonth, endOfMonth);

            if (!existingSalaries.isEmpty()) {
                continue;
            }

            // Calculate salary based on an employment type
            BigDecimal baseSalary = getBigDecimal(staff);

            // Create and save the salary record
            Salary salary = new Salary();
            salary.setStaff(staff);
            salary.setBaseSalary(baseSalary);
            salary.setBonusAmount(BigDecimal.ZERO); // Can be calculated based on performance
            salary.setDeductions(BigDecimal.ZERO);  // Can be calculated based on tax, etc.
            salary.setPaymentDate(paymentDate);
            salary.setPaymentPeriodStart(startOfMonth);
            salary.setPaymentPeriodEnd(endOfMonth);
            salary.setPaymentMethod(Salary.PaymentMethod.BANK_TRANSFER); // Default
            salary.setPaid(false);
            salary.setNotes(notes);

            salaryRepository.save(salary);
        }
    }

    @Override
    public SalaryStatistics getSalaryStatistics(String staffId) {
        // Verify staff exists
        if (!staffRepository.existsById(staffId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Staff not found with ID: " + staffId);
        }

        List<Salary> paidSalaries = salaryRepository.findPaidSalariesByStaffId(staffId);

        return new SalaryStatistics() {
            @Override
            public BigDecimal getTotalPaid() {
                return paidSalaries.stream()
                        .map(s -> s.getBaseSalary()
                                .add(s.getBonusAmount() != null ? s.getBonusAmount() : BigDecimal.ZERO)
                                .subtract(s.getDeductions() != null ? s.getDeductions() : BigDecimal.ZERO))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            @Override
            public BigDecimal getAverageSalary() {
                if (paidSalaries.isEmpty()) {
                    return BigDecimal.ZERO;
                }
                return getTotalPaid().divide(BigDecimal.valueOf(paidSalaries.size()), 2, RoundingMode.HALF_UP);
            }

            @Override
            public int getPaymentCount() {
                return paidSalaries.size();
            }

            @Override
            public LocalDate getLastPaymentDate() {
                return paidSalaries.stream()
                        .map(Salary::getPaymentDate)
                        .max(LocalDate::compareTo)
                        .orElse(null);
            }
        };
    }

    @Override
    public PayrollSummary getPayrollSummary(LocalDate startDate, LocalDate endDate) {
        List<Salary> salaries = salaryRepository.findByPaymentDateBetween(startDate, endDate, null).getContent();

        return new PayrollSummary() {
            @Override
            public int getTotalStaff() {
                return (int) salaries.stream()
                        .map(Salary::getStaff)
                        .distinct()
                        .count();
            }

            @Override
            public BigDecimal getTotalPaid() {
                return salaries.stream()
                        .map(s -> s.getBaseSalary()
                                .add(s.getBonusAmount() != null ? s.getBonusAmount() : BigDecimal.ZERO)
                                .subtract(s.getDeductions() != null ? s.getDeductions() : BigDecimal.ZERO))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            @Override
            public BigDecimal getTotalTax() {
                // Simplified tax calculation (20% of base salary)
                return salaries.stream()
                        .map(s -> s.getBaseSalary().multiply(BigDecimal.valueOf(0.20)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            @Override
            public BigDecimal getTotalBonus() {
                return salaries.stream()
                        .map(s -> s.getBonusAmount() != null ? s.getBonusAmount() : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            @Override
            public BigDecimal getTotalDeductions() {
                return salaries.stream()
                        .map(s -> s.getDeductions() != null ? s.getDeductions() : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            @Override
            public List<SalaryResponse> getTopEarners(int limit) {
                return salaries.stream()
                        .sorted((s1, s2) -> {
                            BigDecimal total1 = s1.getBaseSalary().add(s1.getBonusAmount() != null ? s1.getBonusAmount() : BigDecimal.ZERO);
                            BigDecimal total2 = s2.getBaseSalary().add(s2.getBonusAmount() != null ? s2.getBonusAmount() : BigDecimal.ZERO);
                            return total2.compareTo(total1);
                        })
                        .limit(limit)
                        .map(salaryMapper::toResponse)
                        .collect(Collectors.toList());
            }
        };
    }

    private Salary findSalaryById(String salaryId) {
        return salaryRepository.findById(salaryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Salary not found with ID: " + salaryId));
    }
}
