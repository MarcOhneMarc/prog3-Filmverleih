package com.filmverleih.filmverleih;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class encrypts and decrypts passwords
 */
public class Encryptor {

    /**
     * This method encrypts a password
     * @param password the password
     * @return the encrypted password
     * @throws NoSuchAlgorithmException if the algorithm is not found
     */
    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte[] mDigest = messageDigest.digest(password.getBytes());

        BigInteger bigInteger = new BigInteger(1, mDigest);

        return bigInteger.toString(16);
    }

}
