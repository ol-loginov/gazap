package gazap.site.web.mvc;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("passwordEncoder")
public class PasswordEncoder extends MessageDigestPasswordEncoder {
    public PasswordEncoder() {
        super("MD5", true);
    }
}
