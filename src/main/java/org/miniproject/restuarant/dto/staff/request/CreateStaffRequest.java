package org.miniproject.restuarant.dto.staff.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.miniproject.restuarant.model.Staff;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a new staff member")
public class CreateStaffRequest {

    @NotBlank(message = "User ID is required")
    @Schema(description = "ID of the associated user account", example = "550e8400-e29b-41d4-a716-446655440000")
    private String userID;

    @NotBlank(message = "Employee ID is required")
    @Schema(description = "Unique employee identifier", example = "EMP-001")
    private String employeeID;

    @NotBlank(message = "Position is required")
    @Schema(description = "Job position/title", example = "Senior Chef")
    private String position;

    @NotNull(message = "Hire date is required")
    @Schema(description = "Date when the staff was hired", example = "2023-01-15")
    private LocalDate hireDate;

    @NotNull(message = "Employment type is required")
    @Schema(description = "Type of employment", example = "FULL_TIME", implementation = Staff.EmploymentType.class)
    private Staff.EmploymentType employmentType;

    @DecimalMin(value = "0.0", message = "Hourly rate must be a positive number")
    @Schema(description = "Hourly wage (required for contract/temporary staff)", example = "25.50", nullable = true)
    private BigDecimal hourlyRate;

    @DecimalMin(value = "0.0", message = "Monthly salary must be a positive number")
    @Schema(description = "Monthly salary (required for full-time/part-time staff)", example = "4500.00", nullable = true)
    private BigDecimal monthlySalary;

    @AssertTrue(message = "Either hourly rate or monthly salary must be provided")
    private boolean isValidSalary() {
        return hourlyRate != null || monthlySalary != null;
    }
}
