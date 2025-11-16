package org.miniproject.restuarant.dto.staff.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.miniproject.restuarant.model.Staff;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private String ID;
    private String userID;
    private String employeeID;
    private String position;
    private LocalDate hireDate;
    private Staff.EmploymentType employmentType;
    private BigDecimal hourlyRate;
    private BigDecimal monthlySalary;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // User details that might be needed
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
