package apshomebe.caregility.com.service;

import apshomebe.caregility.com.models.AuthMachine;
import lombok.extern.java.Log;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Log
public class AuthDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String id;
    private String machineName;
    private String machineUri;
    private String machineUriToDisplay;
    private String machineVin;
    private boolean isCloud;
    private boolean isPremises;
    private String username;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineUri() {
        return machineUri;
    }

    public String getMachineUriToDisplay() {
        return machineUriToDisplay;
    }

    public void setMachineUriToDisplay(String machineUriToDisplay) {
        this.machineUriToDisplay = machineUriToDisplay;
    }

    public void setMachineUri(String machineUri) {
        this.machineUri = machineUri;
    }

    public String getMachineVin() {
        return machineVin;
    }

    public void setMachineVin(String machineVin) {
        this.machineVin = machineVin;
    }

    public boolean isCloud() {
        return isCloud;
    }

    public void setCloud(boolean isCloud) {
        this.isCloud = isCloud;
    }

    public boolean isPremises() {
        return isPremises;
    }

    public void setPremises(boolean isPremises) {
        this.isPremises = isPremises;
    }

    private Collection<? extends GrantedAuthority> authorities;

    public AuthDetailsImpl(String id, String machineName, String machineUri, String machineUriToDisplay,
                           String machineVin, boolean isCloud, boolean isPremises) {
        this.id = id;
        this.machineName = machineName;
        this.machineUri = machineUri;
        this.machineVin = machineVin;
        this.isCloud = isCloud;
        this.isPremises = isPremises;
        this.username = machineName;
        this.password = machineUri;
        this.machineUriToDisplay = machineUriToDisplay;

    }

    public static AuthDetailsImpl build(AuthMachine authMachine) {

        return new AuthDetailsImpl(authMachine.getId(), authMachine.getMachineName(), authMachine.getMachineUri(),
                authMachine.getMachineUriToDisplay(), authMachine.getMachineVin(), authMachine.isCloud(),
                authMachine.isPremises());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {

        return username;
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

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthDetailsImpl authImpl = (AuthDetailsImpl) o;
        return Objects.equals(id, authImpl.id);
    }

    @Override
    public String toString() {
        return "AuthDetailsImpl [id=" + id + ", machineName=" + machineName + ", machineUri=" + machineUri
                + ", machineUriToDisplay=" + machineUriToDisplay + ", machineVin=" + machineVin + ", isCloud=" + isCloud
                + ", isPremises=" + isPremises + ", username=" + username + ", password=" + password + ", authorities="
                + authorities + "]";
    }

}
