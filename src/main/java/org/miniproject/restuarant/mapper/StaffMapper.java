package org.miniproject.restuarant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miniproject.restuarant.dto.staff.request.CreateStaffRequest;
import org.miniproject.restuarant.dto.staff.response.StaffResponse;
import org.miniproject.restuarant.model.Staff;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StaffMapper {


    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "employeeID", target = "employeeID")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "hireDate", target = "hireDate")
    @Mapping(source = "employmentType", target = "employmentType")
    @Mapping(source = "hourlyRate", target = "hourlyRate")
    @Mapping(source = "monthlySalary", target = "monthlySalary")
    Staff toEntity(CreateStaffRequest request);


    @Mapping(target = "employeeID", source = "employeeID")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "ID", source = "ID")
    @Mapping(source = "user.ID", target = "userID")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    StaffResponse toResponse(Staff staff);

}
