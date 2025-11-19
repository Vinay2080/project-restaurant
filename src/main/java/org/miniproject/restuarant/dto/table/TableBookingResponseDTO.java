package org.miniproject.restuarant.dto.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableBookingResponseDTO {
    private String ID;
    private String tableID;
    private Integer tableNumber;
    private String customerName;
    private String customerPhone;
    private LocalDateTime bookingTime;
    private LocalDateTime endTime;
    private Integer numberOfGuests;
    private String status;
    private String specialRequests;
}
