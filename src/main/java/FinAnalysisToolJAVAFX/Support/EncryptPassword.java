package FinAnalysisToolJAVAFX.Support;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncryptPassword {

    public static String encrypt(String password1, String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 32;

        PBEKeySpec keySpec = new PBEKeySpec(password1.toCharArray(), username.getBytes(), iterations, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashed = skf.generateSecret(keySpec).getEncoded();

        //System.out.println("The SHA-256 value salted with PBKDF2 is " + decode(hashed));
        return translate_to_string(hashed);
    }

    private static String translate_to_string(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }
}
