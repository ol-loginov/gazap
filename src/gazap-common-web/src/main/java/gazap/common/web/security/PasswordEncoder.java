package gazap.common.web.security;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class PasswordEncoder extends MessageDigestPasswordEncoder {
    public PasswordEncoder() {
        super("MD5", true);
    }
}
