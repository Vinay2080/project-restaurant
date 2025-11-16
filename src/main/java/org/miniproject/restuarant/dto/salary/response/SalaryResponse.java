package org.miniproject.restuarant.dto.salary.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.miniproject.restuarant.model.Salary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryResponse {

    private String ID;
    private String staffID;
    private String employeeName;
    private String position;
    private BigDecimal baseSalary;
    private BigDecimal bonusAmount;
    private BigDecimal deductions;
    private BigDecimal totalAmount;
    private LocalDate paymentDate;
    private LocalDate paymentPeriodStart;
    private LocalDate paymentPeriodEnd;
    private Salary.PaymentMethod paymentMethod;
    private boolean isPaid;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
