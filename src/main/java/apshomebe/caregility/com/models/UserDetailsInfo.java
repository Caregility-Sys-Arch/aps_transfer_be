package apshomebe.caregility.com.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsInfo {
    private String adminId;
    private String environmentUri;
    private String email;

    @Override
    public String toString() {
        return "UserDetailsInfo{" +
                "adminId='" + adminId + '\'' +
                ", environmentUri='" + environmentUri + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
