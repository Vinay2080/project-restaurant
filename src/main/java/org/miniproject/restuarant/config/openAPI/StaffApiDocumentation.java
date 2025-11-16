package org.miniproject.restuarant.config.openAPI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.miniproject.restuarant.dto.staff.request.CreateStaffRequest;
import org.miniproject.restuarant.dto.staff.request.UpdateStaffRequest;
import org.miniproject.restuarant.dto.staff.response.StaffResponse;
import org.miniproject.restuarant.model.Staff;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contains OpenAPI documentation annotations for StaffController.
 * This helps keep the controller clean by moving all documentation to a separate file.
 */
public class StaffApiDocumentation {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Create a new staff member",
            description = "Creates a new staff member with the provided details. Requires ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Staff member created successfully",
                    content = @Content(schema = @Schema(implementation = StaffResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    })
    public @interface CreateStaffApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get staff by ID",
            description = "Retrieves detailed information about a specific staff member by their unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member found",
                    content = @Content(schema = @Schema(implementation = StaffResponse.class))),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public @interface GetStaffByIdApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get staff by user ID",
            description = "Retrieves staff information using the associated user account ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member found",
                    content = @Content(schema = @Schema(implementation = StaffResponse.class))),
            @ApiResponse(responseCode = "404", description = "Staff member not found for the given user ID")
    })
    public @interface GetStaffByUserIdApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get all staff with pagination",
            description = "Retrieves a paginated list of all staff members. Results can be sorted and filtered using pageable parameters."
    )
    public @interface GetAllStaffApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get active/inactive staff",
            description = "Retrieves a paginated list of staff members filtered by their active status."
    )
    public @interface GetStaffByActiveStatusApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get staff by position",
            description = "Retrieves a paginated list of staff members with the specified job position."
    )
    public @interface GetStaffByPositionApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get staff by employment type",
            description = "Retrieves a paginated list of staff members with the specified employment type."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid employment type")
    })
    public @interface GetStaffByEmploymentTypeApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Update staff details",
            description = "Updates the details of an existing staff member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member updated successfully",
                    content = @Content(schema = @Schema(implementation = StaffResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public @interface UpdateStaffApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Deactivate a staff member",
            description = "Deactivates a staff member, preventing them from accessing the system. Requires ADMIN role."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Staff member deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public @interface DeactivateStaffApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Reactivate a staff member",
            description = "Reactivates a previously deactivated staff member. Requires ADMIN role."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Staff member reactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public @interface ReactivateStaffApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Delete a staff member",
            description = "Permanently deletes a staff member from the system. This action cannot be undone. Requires ADMIN role."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Staff member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    public @interface DeleteStaffApiDoc {
    }

    // Parameter annotations
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(description = "ID of the staff member", required = true,
            example = "550e8400-e29b-41d4-a716-446655440000")
    public @interface StaffIdParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(description = "ID of the user account", required = true,
            example = "5a021177-7f16-46cb-a72c-be3a68e892c0")
    public @interface UserIdParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(description = "Job position to filter by", required = true,
            example = "Senior Chef")
    public @interface PositionParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(description = "Filter by active status", example = "true")
    public @interface ActiveStatusParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(description = "Employment type to filter by", required = true,
            schema = @Schema(implementation = Staff.EmploymentType.class))
    public @interface EmploymentTypeParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Staff member details",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateStaffRequest.class)
            )
    )
    public @interface CreateStaffRequestBody {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated staff member details",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateStaffRequest.class)
            )
    )
    public @interface UpdateStaffRequestBody {
    }
}
