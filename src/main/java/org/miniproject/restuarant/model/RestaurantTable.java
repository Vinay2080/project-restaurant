package org.miniproject.restuarant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "restaurant_tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RestaurantTable extends BaseEntity {
    
    @Column(nullable = false, unique = true)
    private Integer tableNumber;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TableStatus status = TableStatus.AVAILABLE;
    
    private String location;
    
    public enum TableStatus {
        AVAILABLE, RESERVED, OCCUPIED, MAINTENANCE
    }
}
