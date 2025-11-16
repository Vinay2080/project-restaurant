package org.miniproject.restuarant.mapper;

import org.mapstruct.*;
import org.miniproject.restuarant.dto.admin.request.CreateManagerRequest;
import org.miniproject.restuarant.dto.admin.request.UpdateManagerRequest;
import org.miniproject.restuarant.dto.admin.response.ManagerResponse;
import org.miniproject.restuarant.model.AppUser;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManagerMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "profilePictureUrl", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdDate", ignore = true)

    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "emailVerified", constant = "false")
    @Mapping(target = "phoneVerified", constant = "false")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "locked", constant = "false")
    @Mapping(target = "credentialsExpired", constant = "false")
    AppUser createManagerMapper(CreateManagerRequest createManagerRequest);

    @Mapping(target = "ID", source = "ID")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "locked", source = "locked")
    @Mapping(target = "credentialsExpired", source = "credentialsExpired")
    @Mapping(target = "emailVerified", source = "emailVerified")
    @Mapping(target = "phoneVerified", source = "phoneVerified")
    @Mapping(target = "profilePictureUrl", source = "profilePictureUrl")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "lastModifiedDate", source = "lastModifiedDate")
    ManagerResponse toManagerResponse(AppUser appUser);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "profilePictureUrl", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "credentialsExpired", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "ID", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateManagerMapper(UpdateManagerRequest createManagerRequest, @MappingTarget AppUser appUser);
}