package org.miniproject.restuarant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miniproject.restuarant.dto.table.TableBookingRequestDTO;
import org.miniproject.restuarant.dto.table.TableBookingResponseDTO;
import org.miniproject.restuarant.model.TableBooking;
import org.miniproject.restuarant.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class TableBookingMapper {

    @Autowired
    protected RestaurantTableRepository tableRepository;




    @Mapping(target = "table", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "user", ignore = true) // Will be set in service
//    @Mapping(target = "table", source = "tableID")
    @Mapping(target = "status", ignore = true) // Default status will be set in the entity
    @Mapping(target = "endTime", ignore = true) // Will be calculated in service
    public abstract TableBooking toEntity(TableBookingRequestDTO dto);

//    @Mapping(target = "tableID", source = "table.ID")
//    @Mapping(target = "tableNumber", source = "table.tableNumber")
    @Mapping(target = "tableNumber", ignore = true)
    @Mapping(target = "tableID", ignore = true)
    @Mapping(target = "status", expression = "java(booking.getStatus().name())")
    public abstract TableBookingResponseDTO toDTO(TableBooking booking);

}
