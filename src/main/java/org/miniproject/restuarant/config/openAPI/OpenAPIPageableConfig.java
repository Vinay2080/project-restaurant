package org.miniproject.restuarant.config.openAPI;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.core.converters.models.PageableAsQueryParam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
        in = ParameterIn.QUERY,
        name = "page",
        description = "Zero-based page index (0..N)",
        schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "size",
        description = "Number of items per page",
        schema = @Schema(type = "integer", defaultValue = "20")
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "sort",
        description = "Sorting criteria in the format: property(,asc|desc). " +
                "Default sort order is ascending. " +
                "Multiple sort criteria are supported.",
        array = @ArraySchema(schema = @Schema(type = "string")),
        example = "lastName,asc&sort=createdAt,desc"
)
@PageableAsQueryParam
public @interface OpenAPIPageableConfig {
    // This annotation will be used to document Pageable parameters
}
