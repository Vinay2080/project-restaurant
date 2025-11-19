package org.miniproject.restuarant.repository;

import org.miniproject.restuarant.model.RestaurantTable;
import org.miniproject.restuarant.model.RestaurantTable.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, String> {
    List<RestaurantTable> findByStatus(TableStatus status);
    List<RestaurantTable> findByCapacityGreaterThanEqual(Integer minCapacity);
    boolean existsByTableNumber(Integer tableNumber);

    RestaurantTable findByID(String tableID);

    boolean existsByID(String id);
}
