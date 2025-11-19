package org.miniproject.restuarant.dto.authentication.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUserResponse {

    @Schema(example = "admin@restaurant.com")
    private String email;

    @Schema(example = "['ROLE_ADMIN','ROLE_MANAGER']")
    private List<String> roles;
}
