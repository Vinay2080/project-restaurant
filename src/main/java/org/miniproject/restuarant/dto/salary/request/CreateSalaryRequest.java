package org.miniproject.restuarant.dto.salary.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.miniproject.restuarant.model.Salary;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a new salary record")
public class CreateSalaryRequest {

    @NotBlank(message = "Staff ID is required")
    @Schema(description = "ID of the staff member", example = "550e8400-e29b-41d4-a716-446655440000")
    private String staffID;

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", message = "Base salary must be a positive number")
    @Schema(description = "Base salary amount", example = "50000.00")
    private BigDecimal baseSalary;

    @DecimalMin(value = "0.0", message = "Bonus amount must be a positive number")
    @Schema(description = "Optional bonus amount", example = "5000.00", nullable = true)
    private BigDecimal bonusAmount;

    @DecimalMin(value = "0.0", message = "Deductions must be a positive number")
    @Schema(description = "Optional deductions from salary", example = "1000.00", nullable = true)
    private BigDecimal deductions;

    @NotNull(message = "Payment date is required")
    @Schema(description = "Date when the payment was made", example = "2023-12-31")
    private LocalDate paymentDate;

    @NotNull(message = "Payment period start date is required")
    @Schema(description = "Start date of the payment period", example = "2023-12-01")
    private LocalDate paymentPeriodStart;

    @NotNull(message = "Payment period end date is required")
    @Schema(description = "End date of the payment period", example = "2023-12-31")
    private LocalDate paymentPeriodEnd;

    @NotNull(message = "Payment method is required")
    private Salary.PaymentMethod paymentMethod;

    @Schema(description = "Additional notes about the salary payment", example = "Bonus for Q4 performance")
    private String notes;


}
