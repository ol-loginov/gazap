package waypalm.common.web.security;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

@AppSecurity
public class PasswordEncoder extends MessageDigestPasswordEncoder {
    public PasswordEncoder() {
        super("SHA-256", true);
    }
}
