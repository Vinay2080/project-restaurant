package org.miniproject.restuarant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miniproject.restuarant.dto.salary.request.CreateSalaryRequest;
import org.miniproject.restuarant.dto.salary.response.SalaryResponse;
import org.miniproject.restuarant.model.Salary;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SalaryMapper {


    // Default to unpaid
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isPaid", constant = "false")
    Salary toEntity(CreateSalaryRequest request);


    @Mapping(target = "totalAmount", expression = "java(calculateTotalAmount(salary))")
    @Mapping(target = "staffID", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "isPaid", ignore = true)
    @Mapping(target = "employeeName", ignore = true)
    SalaryResponse toResponse(Salary salary);


    default BigDecimal calculateTotalAmount(Salary salary) {
        if (salary == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = salary.getBaseSalary() != null ? salary.getBaseSalary() : BigDecimal.ZERO;
        total = total.add(salary.getBonusAmount() != null ? salary.getBonusAmount() : BigDecimal.ZERO);
        total = total.subtract(salary.getDeductions() != null ? salary.getDeductions() : BigDecimal.ZERO);

        return total.max(BigDecimal.ZERO);
    }
}
