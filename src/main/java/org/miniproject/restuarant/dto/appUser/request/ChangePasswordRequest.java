package org.miniproject.restuarant.dto.appUser.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

public class ChangePasswordRequest implements Serializable {

    @NotBlank(message = "Current password is required")
    @Schema(example = "pAssword1!_")
    private String password;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).*$",
            message = "Password must contain at least one digit, one lowercase, one uppercase letter and one special character"
    )
    @Schema(example = "pAssword2!_")
    private String newPassword;

    @NotBlank(message = "Please confirm your new password")
    @Schema(example = "pAssword2!_")
    private String confirmPassword;

}