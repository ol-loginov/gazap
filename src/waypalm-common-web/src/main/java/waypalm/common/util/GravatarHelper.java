package waypalm.common.util;

import org.springframework.util.StringUtils;

public class GravatarHelper {
    private static final String EMPTY_HASH = "00000000000000000000000000000000";

    public static String hashOrDefault(String email) {
        if (email != null && StringUtils.hasText(email)) {
            return HashUtil.md5(email);
        }
        return EMPTY_HASH;
    }
}
