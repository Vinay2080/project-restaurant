package org.miniproject.restuarant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.table.TableBookingRequestDTO;
import org.miniproject.restuarant.dto.table.TableBookingResponseDTO;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.service.TableBookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
@Tag(name = "Bookings", description = "Table Booking Management API")
public class TableBookingController {

    private final TableBookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public void bookTable(
            @Valid
            @RequestBody final TableBookingRequestDTO bookingRequest,
            @AuthenticationPrincipal final UserDetails userDetails
    ) {
        // In a real application, you would get the user ID from the authentication token
        // This is a placeholder - replace with actual user ID extraction
        final String userID = ((AppUser) userDetails).getID();
        bookingService.bookTable(bookingRequest, userID);
    }

    @GetMapping("/{bookingID}")
    @PreAuthorize("isAuthenticated()")
    public TableBookingResponseDTO getBookingById(
            @PathVariable final String bookingID
    ) {
        return bookingService.getBookingById(bookingID);
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("isAuthenticated()")
    public List<TableBookingResponseDTO> getMyBookings(
            @AuthenticationPrincipal final UserDetails userDetails
    ) {
        final String userId = ((AppUser) userDetails).getID(); // Replace with: ((AppUser) userDetails).getId();
        return bookingService.getBookingsByUser(userId);
    }

    @GetMapping("/table/{tableID}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public List<TableBookingResponseDTO> getBookingsByTable(
            @PathVariable final String tableID
    ) {
        return bookingService.getBookingsByTable(tableID);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STAFF')")
    public List<TableBookingResponseDTO> getBookingsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime endDate
    ) {
        return bookingService.getBookingsByDateRange(startDate, endDate);
    }

    @PatchMapping("/{bookingID}/status/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public void updateBookingStatus(
            @PathVariable final String bookingID,
            @PathVariable final String status
    ) {
        bookingService.updateBookingStatus(bookingID, status);
    }

    @DeleteMapping("/{bookingID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void cancelBooking(
            @PathVariable final String bookingID
    ) {
        // Add an authorization check to ensure the user owns this booking
        bookingService.cancelBooking(bookingID);
    }

    @GetMapping("/availability")
    public boolean checkTableAvailability(
            @RequestParam final String tableID,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime endTime
    ) {
        return bookingService.isTableAvailable(tableID, startTime, endTime);
    }
}
