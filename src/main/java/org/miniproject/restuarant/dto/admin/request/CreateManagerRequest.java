package org.miniproject.restuarant.dto.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.miniproject.restuarant.model.AppUser}
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor

public class CreateManagerRequest implements Serializable {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    @Schema(example = "manager")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    @Schema(example = "one")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Schema(example = "managerone@mail.com")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Please provide a valid phone number (e.g., +1234567890 or 1234567890)"
    )
    @Schema(example = "+919903428111")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"
    )
    @Schema(example = "Manager@123!")
    private String password;

    @NotBlank(message = "Please confirm your password")
    @Schema(example = "Manager@123!")
    private String confirmPassword;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Schema(example = "2003-03-03")
    private LocalDate dateOfBirth;
}