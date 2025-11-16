package org.miniproject.restuarant.dto.authentication.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequest {
    private String refreshToken;

}
