package org.miniproject.restuarant.mapper;

import org.mapstruct.*;
import org.miniproject.restuarant.dto.appUser.request.ProfileUpdateRequest;
import org.miniproject.restuarant.dto.authentication.request.RegistrationRequest;
import org.miniproject.restuarant.model.AppUser;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppUserMapper {

    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "profilePictureUrl", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)

    @Mapping(target = "emailVerified", constant = "false")
    @Mapping(target = "phoneVerified", constant = "false")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "locked", constant = "false")
    @Mapping(target = "credentialsExpired", constant = "false")
    AppUser registrationMapper(RegistrationRequest registrationRequest);

    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "credentialsExpired", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void profileUpdateMapper(ProfileUpdateRequest profileUpdateRequest, @MappingTarget AppUser appUser);

}