package apshomebe.caregility.com.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTokenRequest {
    private String accessToken;
    private String environmentUrl;
}
