package org.miniproject.restuarant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "staff")
public class Staff extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;

    @Column(name = "employee_id", unique = true, nullable = false)
    private String employeeID;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "employment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(name = "hourly_rate")
    private BigDecimal hourlyRate;

    @Column(name = "monthly_salary")
    private BigDecimal monthlySalary;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum EmploymentType {
        FULL_TIME,
        PART_TIME,
        CONTRACT,
        TEMPORARY
    }
}
