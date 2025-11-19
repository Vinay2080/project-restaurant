package org.miniproject.restuarant.serviceImplementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miniproject.restuarant.dto.table.TableBookingRequestDTO;
import org.miniproject.restuarant.dto.table.TableBookingResponseDTO;
import org.miniproject.restuarant.exception.BusinessException;
import org.miniproject.restuarant.exception.ErrorCode;
import org.miniproject.restuarant.mapper.TableBookingMapper;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.model.RestaurantTable;
import org.miniproject.restuarant.model.TableBooking;
import org.miniproject.restuarant.repository.AppUserRepository;
import org.miniproject.restuarant.repository.RestaurantTableRepository;
import org.miniproject.restuarant.repository.TableBookingRepository;
import org.miniproject.restuarant.service.TableBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableBookingServiceImpl implements TableBookingService {

    private final TableBookingRepository bookingRepository;
    private final RestaurantTableRepository tableRepository;
    private final AppUserRepository userRepository;
    private final TableBookingMapper bookingMapper;
    
    private static final int DEFAULT_BOOKING_DURATION_HOURS = 2;

    @Override
    @Transactional
    public void bookTable(TableBookingRequestDTO bookingRequest, String userID) {
        // Validate table exists and is available
        RestaurantTable table = tableRepository.findByID(bookingRequest.getTableID());
        
        // Validate user exists
        AppUser user = userRepository.findById(userID)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "User not found with id: " + userID));
        
        // Calculate end time (default 2 hours from booking time)
        LocalDateTime endTime = bookingRequest.getBookingTime().plusHours(DEFAULT_BOOKING_DURATION_HOURS);
        
        // Check if table is available for the requested time slot
        if (!isTableAvailable(bookingRequest.getTableID(), bookingRequest.getBookingTime(), endTime)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Table is not available for the selected time slot");
        }
        
        // Create and save the booking
        TableBooking booking = bookingMapper.toEntity(bookingRequest);
        booking.setUser(user);
        booking.setTable(table);
        booking.setEndTime(endTime);
        
        booking = bookingRepository.save(booking);
        
        // Update table status if needed
        if (booking.getBookingTime().isBefore(LocalDateTime.now().plusHours(1))) {
            // If booking is for soon, mark table as reserved
            table.setStatus(RestaurantTable.TableStatus.RESERVED);
            tableRepository.save(table);
        }

        bookingMapper.toDTO(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public TableBookingResponseDTO getBookingById(String bookingID) {
        return bookingRepository.findById(bookingID)
                .map(bookingMapper::toDTO)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found with id: " + bookingID));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableBookingResponseDTO> getBookingsByUser(String userID) {
        return bookingRepository.findByUserID(userID).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableBookingResponseDTO> getBookingsByTable(String tableID) {
        if (tableRepository.existsByID((tableID))) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Table not found with id: " + tableID);
        }
        
        return bookingRepository.findByTableID(tableID).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableBookingResponseDTO> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Start date must be before end date");
        }
        
        return bookingRepository.findBookingsBetweenDates(startDate, endDate).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateBookingStatus(String bookingID, String status) {
        TableBooking booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found with id: " + bookingID));
        
        try {
            TableBooking.BookingStatus newStatus = TableBooking.BookingStatus.valueOf(status.toUpperCase());
            booking.setStatus(newStatus);
            
            // If booking is completed or cancelled, check if we can make the table available
            if (newStatus == TableBooking.BookingStatus.COMPLETED || 
                newStatus == TableBooking.BookingStatus.CANCELLED) {
                
                // Check if there are no other active bookings for this table
                getTime(booking);
            }
            
            booking = bookingRepository.save(booking);
            bookingMapper.toDTO(booking);

        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Invalid status: " + status);
        }
    }

    @Override
    @Transactional
    public void cancelBooking(String bookingID) {
        TableBooking booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Booking not found with id: " + bookingID));
        
        booking.setStatus(TableBooking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        
        // Check if we can make the table available
        getTime(booking);
    }

    private void getTime(TableBooking booking) {
        LocalDateTime now = LocalDateTime.now();
        List<TableBooking> activeBookings = bookingRepository.findConflictingBookings(
                booking.getTable().getID(),
                now,
                now.plusHours(DEFAULT_BOOKING_DURATION_HOURS)
        );

        if (activeBookings.isEmpty() ||
            activeBookings.stream().allMatch(b -> b.getStatus() != TableBooking.BookingStatus.CONFIRMED)) {
            // No active bookings, mark table as available
            RestaurantTable table = booking.getTable();
            table.setStatus(RestaurantTable.TableStatus.AVAILABLE);
            tableRepository.save(table);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTableAvailable(String tableID, LocalDateTime startTime, LocalDateTime endTime) {
        // Check if table exists
        if (tableRepository.existsByID(tableID)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Table not found with id: " + tableID);
        }
        
        // Get all conflicting bookings
        List<TableBooking> conflicts = bookingRepository.findConflictingBookings(
                tableID, startTime, endTime);
        
        return conflicts.isEmpty();
    }
}
