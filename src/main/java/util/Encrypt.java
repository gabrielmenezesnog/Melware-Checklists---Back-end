package util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encrypt {
    public static String md5(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, messageDigest.digest(password.getBytes()));
            return hash.toString(16);
        } catch (Exception e) {
            return "";
        }
    }
}