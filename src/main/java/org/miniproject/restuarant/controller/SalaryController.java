package org.miniproject.restuarant.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.config.openAPI.SalaryApiDocumentation.*;
import org.miniproject.restuarant.dto.salary.request.CreateSalaryRequest;
import org.miniproject.restuarant.dto.salary.response.SalaryResponse;
import org.miniproject.restuarant.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/salaries")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping
    @CreateSalaryApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public SalaryResponse createSalary(
            @CreateSalaryRequestBody
            @RequestBody @Valid CreateSalaryRequest request) {
        return salaryService.createSalary(request);
    }

    @GetMapping("/{salaryId}")
    @GetSalaryByIdApiDoc
    public SalaryResponse getSalaryById(
            @SalaryIdParam @PathVariable("salaryId") String salaryId) {
        return salaryService.getSalaryById(salaryId);
    }

    @GetMapping("/staff/{staffId}")
    @GetSalariesByStaffIdApiDoc
    public Page<SalaryResponse> getSalariesByStaffId(
            @StaffIdParam @PathVariable String staffId,
            @PageableDefault(size = 12, sort = "paymentDate,desc") Pageable pageable) {
        return salaryService.getSalariesByStaffId(staffId, pageable);
    }

    @GetMapping
    @GetSalariesByDateRangeApiDoc
    public Page<SalaryResponse> getSalariesByDateRange(
            @StartDateParam @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @EndDateParam @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(size = 20, sort = "paymentDate,desc") Pageable pageable) {
        return salaryService.getSalariesByDateRange(startDate, endDate, pageable);
    }

    @GetMapping("/status/{isPaid}")
    @GetSalariesByPaymentStatusApiDoc
    public Page<SalaryResponse> getSalariesByPaymentStatus(
            @PaymentStatusParam @PathVariable boolean isPaid,
            @PageableDefault(size = 20, sort = "paymentDate,desc") Pageable pageable) {
        return salaryService.getSalariesByPaymentStatus(isPaid, pageable);
    }

    @PatchMapping("/{salaryId}/mark-paid")
    @MarkAsPaidApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public SalaryResponse markAsPaid(
            @SalaryIdParam @PathVariable String salaryId) {
        return salaryService.markAsPaid(salaryId);
    }

    @DeleteMapping("/{salaryId}")
    @DeleteSalaryApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSalary(
            @SalaryIdParam @PathVariable String salaryId) {
        salaryService.deleteSalary(salaryId);
    }

    @PostMapping("/generate-monthly")
    @GenerateMonthlySalariesApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public void generateMonthlySalaries(
            @Parameter(
                    name = "paymentDate",
                    description = "The date when the payment is being made",
                    required = true,
                    example = "2023-12-31"
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate paymentDate,
            @Parameter(
                    name = "notes",
                    description = "Additional notes about the salary generation",
                    example = "Year-end bonus included"
            )
            @RequestParam(required = false) String notes) {
        salaryService.generateMonthlySalaries(paymentDate, notes);
    }

    @GetMapping("/staff/{staffId}/statistics")
    @GetSalaryStatisticsApiDoc
    public SalaryService.SalaryStatistics getSalaryStatistics(
            @StaffIdParam @PathVariable String staffId) {
        return salaryService.getSalaryStatistics(staffId);
    }

    @GetMapping("/payroll-summary")
    @GetPayrollSummaryApiDoc
    public SalaryService.PayrollSummary getPayrollSummary(
            @StartDateParam @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @EndDateParam @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return salaryService.getPayrollSummary(startDate, endDate);
    }
}
