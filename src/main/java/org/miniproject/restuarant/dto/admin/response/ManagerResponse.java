package org.miniproject.restuarant.dto.admin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Manager details response")
public class ManagerResponse {
    @Schema(description = "Unique identifier of the manager", example = "123e4567-e89b-12d3-a456-426614174000")
    private String ID;

    @Schema(description = "First name of the manager", example = "John")
    private String firstName;

    @Schema(description = "Last name of the manager", example = "Doe")
    private String lastName;

    @Schema(description = "Email address of the manager", example = "john.doe@restaurant.com")
    private String email;

    @Schema(description = "Phone number of the manager", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Indicates if the manager account is enabled", example = "true")
    private boolean enabled;

    @Schema(description = "Indicates if the manager account is locked", example = "false")
    private boolean locked;

    @Schema(description = "Indicates if the manager's credentials have expired", example = "false")
    @JsonProperty("credentialsExpired")
    private boolean credentialsExpired;

    @Schema(description = "Indicates if the manager's email is verified", example = "true")
    @JsonProperty("emailVerified")
    private boolean emailVerified;

    @Schema(description = "Indicates if the manager's phone number is verified", example = "true")
    @JsonProperty("phoneVerified")
    private boolean phoneVerified;

    @Schema(description = "URL of the manager's profile picture", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;

    @Schema(description = "Date and time when the manager account was created", example = "2023-01-01T12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @Schema(description = "Date and time when the manager account was last modified", example = "2023-01-01T12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastModifiedDate;
}