package org.miniproject.restuarant.repository;

import org.miniproject.restuarant.model.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableBookingRepository extends JpaRepository<TableBooking, String> {

    List<TableBooking> findByTableID(String tableID);
    
    List<TableBooking> findByUserID(String userID);

    
    @Query("SELECT b FROM TableBooking b WHERE " +
           "b.table.ID = :tableID AND " +
           "b.bookingTime < :endTime AND " +
           "b.endTime > :startTime AND " +
           "b.status = org.miniproject.restuarant.model.TableBooking.BookingStatus.CONFIRMED")
    List<TableBooking> findConflictingBookings(
            @Param("tableID") String tableID,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT b FROM TableBooking b WHERE " +
           "b.bookingTime >= :startDate AND " +
           "b.bookingTime < :endDate")
    List<TableBooking> findBookingsBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
