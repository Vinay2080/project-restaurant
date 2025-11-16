package org.miniproject.restuarant.config.openAPI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.miniproject.restuarant.dto.salary.request.CreateSalaryRequest;
import org.miniproject.restuarant.dto.salary.response.SalaryResponse;
import org.miniproject.restuarant.service.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contains OpenAPI documentation annotations for SalaryController.
 * This helps keep the controller clean by moving all documentation to a separate file.
 */
public class SalaryApiDocumentation {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Create a new salary record",
            description = "Creates a new salary record for a staff member. Requires ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Salary record created successfully",
                    content = @Content(schema = @Schema(implementation = SalaryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Staff member not found"
            )
    })
    public @interface CreateSalaryApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get salary by ID",
            description = "Retrieves detailed information about a specific salary record."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Salary record found",
                    content = @Content(schema = @Schema(implementation = SalaryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Salary record not found"
            )
    })
    public @interface GetSalaryByIdApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get salaries by staff ID",
            description = "Retrieves a paginated list of salary records for a specific staff member."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of salary records retrieved successfully"
            )
    })
    public @interface GetSalariesByStaffIdApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get salaries by date range",
            description = "Retrieves a paginated list of salary records within the specified date range."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of salary records retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid date range (start date is after end date)"
            )
    })
    public @interface GetSalariesByDateRangeApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get salaries by payment status",
            description = "Retrieves a paginated list of salary records filtered by payment status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of salary records retrieved successfully"
            )
    })
    public @interface GetSalariesByPaymentStatusApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Mark salary as paid",
            description = "Marks a salary record as paid. Requires ADMIN role."
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Salary marked as paid successfully",
                    content = @Content(schema = @Schema(implementation = SalaryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Salary is already marked as paid"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Salary record not found"
            )
    })
    public @interface MarkAsPaidApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Delete salary record",
            description = "Permanently deletes a salary record. Requires ADMIN role."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Salary record deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Salary record not found"
            )
    })
    public @interface DeleteSalaryApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Generate monthly salaries",
            description = "Generates monthly salary records for all active staff members. Requires ADMIN role."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Monthly salaries generated successfully"
            )
    })
    public @interface GenerateMonthlySalariesApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get salary statistics",
            description = "Retrieves salary statistics for a specific staff member."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Salary statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SalaryService.SalaryStatistics.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Staff member not found"
            )
    })
    public @interface GetSalaryStatisticsApiDoc {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Get payroll summary",
            description = "Retrieves a payroll summary for the specified date range."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payroll summary retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SalaryService.PayrollSummary.class))
            )
    })
    public @interface GetPayrollSummaryApiDoc {
    }

    // Parameter annotations
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(
            name = "salaryId",
            description = "ID of the salary record",
            required = true,
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    public @interface SalaryIdParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(
            name = "staffId",
            description = "ID of the staff member",
            required = true,
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    public @interface StaffIdParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(
            name = "isPaid",
            description = "Payment status filter",
            example = "true"
    )
    public @interface PaymentStatusParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(
            name = "startDate",
            description = "Start date of the period (inclusive)",
            required = true,
            example = "2023-01-01"
    )
    public @interface StartDateParam {
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(
            name = "endDate",
            description = "End date of the period (inclusive)",
            required = true,
            example = "2023-12-31"
    )
    public @interface EndDateParam {
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Create a new salary record",
            description = "Creates a new salary record for an employee with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Salary details to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateSalaryRequest.class),
                            examples = @ExampleObject(
                                    name = "CreateSalaryExample",
                                    value = """
                                            {
                                              "staffID": "123e4567-e89b-12d3-a456-426614174000",
                                              "baseSalary": 50000.00,
                                              "bonusAmount": 5000.00,
                                              "deductions": 1000.00,
                                              "paymentDate": "2023-12-31",
                                              "paymentPeriodStart": "2023-12-01",
                                              "paymentPeriodEnd": "2023-12-31",
                                              "paymentMethod": "BANK_TRANSFER",
                                              "notes": "Bonus for Q4 performance"
                                            }"""
                            )
                    )
            )
    )
    public @interface CreateSalaryRequestBody {
    }
}
