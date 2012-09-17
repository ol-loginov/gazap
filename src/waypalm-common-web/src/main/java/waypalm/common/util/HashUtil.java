package waypalm.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class HashUtil {
    private static final MessageDigest md;
    private static final MessageDigest sha256;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 is not available");
        }

        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is not available");
        }
    }

    public static byte[] md5(byte[] input) {
        if (md == null) {
            throw new IllegalStateException("MD5 digest is not available");
        }

        synchronized (md) {
            md.reset();
            return md.digest(input);
        }
    }

    public static String md5(String input) {
        return md5(input, true);
    }

    public static String md5(String input, boolean lowerCase) {
        if (lowerCase) {
            input = input.toLowerCase(Locale.ENGLISH);
        }
        BigInteger inputHash = new BigInteger(1, md5(input.getBytes()));
        return inputHash.toString(16).toLowerCase(Locale.ENGLISH);
    }

    public static byte[] sha256(byte[] bytes) {
        if (sha256 == null) {
            throw new IllegalStateException("SHA-256 digest is not available");
        }
        synchronized (sha256) {
            sha256.reset();
            return sha256.digest(bytes);
        }
    }

    public static String sha256(String text) {
        return sha256(text, false);
    }

    public static String sha256(String text, boolean lowerCase) {
        if (lowerCase) {
            text = text.toLowerCase(Locale.ENGLISH);
        }
        BigInteger inputHash = new BigInteger(1, sha256(text.getBytes()));
        return inputHash.toString(16).toLowerCase(Locale.ENGLISH);
    }
}
