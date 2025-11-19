package org.miniproject.restuarant.service;

import org.miniproject.restuarant.dto.table.TableBookingRequestDTO;
import org.miniproject.restuarant.dto.table.TableBookingResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TableBookingService {
    
    void bookTable(TableBookingRequestDTO bookingRequest, String userID);
    
    TableBookingResponseDTO getBookingById(String bookingID);
    
    List<TableBookingResponseDTO> getBookingsByUser(String userID);
    
    List<TableBookingResponseDTO> getBookingsByTable(String tableID);
    
    List<TableBookingResponseDTO> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    void updateBookingStatus(String bookingID, String status);
    
    void cancelBooking(String bookingID);
    
    boolean isTableAvailable(String tableID, LocalDateTime startTime, LocalDateTime endTime);
}
