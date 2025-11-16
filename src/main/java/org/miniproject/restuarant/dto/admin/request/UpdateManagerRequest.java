package org.miniproject.restuarant.dto.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link org.miniproject.restuarant.model.AppUser}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Schema(description = "Request object for updating manager details")
public class UpdateManagerRequest implements Serializable {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    @Schema(description = "Manager's first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    @Schema(description = "Manager's last name", example = "Doe")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Schema(description = "Manager's email address", example = "john.doe@example.com")
    private String email;

    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Please provide a valid phone number (e.g., +1234567890 or 1234567890)"
    )
    @Schema(description = "Manager's contact number", example = "+1234567890", nullable = true)
    private String phoneNumber;
}