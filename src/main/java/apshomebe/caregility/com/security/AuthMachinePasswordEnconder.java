package apshomebe.caregility.com.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthMachinePasswordEnconder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.toString().equals(s);
    }
}