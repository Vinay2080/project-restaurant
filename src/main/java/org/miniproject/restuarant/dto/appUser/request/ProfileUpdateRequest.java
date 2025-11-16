package org.miniproject.restuarant.dto.appUser.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.miniproject.restuarant.model.AppUser}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProfileUpdateRequest implements Serializable {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Schema(example = "john")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(example = "doe")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Schema(example = "2000-01-01")
    private LocalDate dateOfBirth;

    private String profilePictureUrl;
    @NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.BLANK")
    @Email(message = "VALIDATION.REGISTRATION.EMAIL.FORMAT")
    @Schema(example = "user002@mail.com")

    private String email;

    @NotBlank(message = "VALIDATION.REGISTRATION.PHONE.BLANK")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "VALIDATION.REGISTRATION.PHONE.FORMAT"
    )
    @Schema(example = "+919903428992")
    private String phoneNumber;


}