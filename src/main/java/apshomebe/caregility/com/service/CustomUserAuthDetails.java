package apshomebe.caregility.com.service;

import apshomebe.caregility.com.models.AuthMachine;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Log4j2
public class CustomUserAuthDetails implements UserDetails {

    private String adminId;
    private String environmentUri;
    private String email;
    private String username;
    private String password;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getEnvironmentUri() {
        return environmentUri;
    }

    public void setEnvironmentUri(String environmentUri) {
        this.environmentUri = environmentUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return environmentUri;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public  CustomUserAuthDetails build( String adminId,String email,String environmentUri) {

     this.adminId=adminId;
     this.email=email;
     this.environmentUri=environmentUri;
     return this;

    }

    @Override
    public boolean equals(Object o) {
        log.info("inside the equals methode of the custom user Auth details ");
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserAuthDetails authImpl = (CustomUserAuthDetails) o;
        return Objects.equals(adminId, authImpl.adminId);
    }

    @Override
    public String toString() {
        return "CustomUserAuthDetails{" +
                "adminId='" + adminId + '\'' +
                ", environmentUri='" + environmentUri + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
