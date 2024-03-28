package com.filmverleih.filmverleih;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte[] mDigest = messageDigest.digest(password.getBytes());

        BigInteger bigInteger = new BigInteger(1, mDigest);

        return bigInteger.toString(16);
    }

}
