package com.primebank.ejb.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String hashedPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] hashedPassword = messageDigest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();

            for(byte b: hashedPassword){
                String hex = Integer.toHexString(0xff & b);

                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: "+e);
        }
    }
}
