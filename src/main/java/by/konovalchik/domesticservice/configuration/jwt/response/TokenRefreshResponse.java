package by.konovalchik.domesticservice.configuration.jwt.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRefreshResponse {

    private String accessToken;
    private String refreshToken;
    private final String tokenType = "Bearer";


}
