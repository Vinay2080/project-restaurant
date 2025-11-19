package org.miniproject.restuarant.dto.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.miniproject.restuarant.model.RestaurantTable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    private Integer tableNumber;
    private Integer capacity;
    private RestaurantTable.TableStatus status;
    private String location;
}
