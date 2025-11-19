package org.miniproject.restuarant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miniproject.restuarant.dto.table.TableDTO;
import org.miniproject.restuarant.model.RestaurantTable;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TableMapper {
    

    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "ID", ignore = true)
    RestaurantTable toEntity(TableDTO dto);
    
    TableDTO toDTO(RestaurantTable entity);
    
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "ID", ignore = true)
    void updateEntityFromDTO(TableDTO dto, @MappingTarget RestaurantTable entity);
}
