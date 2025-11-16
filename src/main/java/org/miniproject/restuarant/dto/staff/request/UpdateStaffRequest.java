package org.miniproject.restuarant.dto.staff.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.miniproject.restuarant.model.Staff;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for updating staff member details")
public class UpdateStaffRequest {

    @Schema(description = "Updated job position/title", example = "Head Chef")
    private String position;

    @Schema(description = "Updated employment type", example = "FULL_TIME", implementation = Staff.EmploymentType.class)
    private Staff.EmploymentType employmentType;

    @DecimalMin(value = "0.0", message = "Hourly rate must be a positive number")
    @Schema(description = "Updated hourly wage (for contract/temporary staff)", example = "27.50", nullable = true)
    private BigDecimal hourlyRate;

    @DecimalMin(value = "0.0", message = "Monthly salary must be a positive number")
    @Schema(description = "Updated monthly salary (for full-time/part-time staff)", example = "4800.00", nullable = true)
    private BigDecimal monthlySalary;

    @Schema(description = "Updated active status of the staff member", example = "true")
    private Boolean isActive;

}
