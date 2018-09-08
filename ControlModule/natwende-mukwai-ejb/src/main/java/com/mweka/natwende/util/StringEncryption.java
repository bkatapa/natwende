package com.mweka.natwende.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;

public class StringEncryption {

    public static final String PIN_SALT = "b50d9533dc314dd6b53302f72e4363ce";

    public static String generateSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String encryptString(String inputString, String salt) {
        MessageDigest md;
        try {
            String passwordToHash;
            if (salt != null) {
                passwordToHash = inputString + salt;
            } else {
                passwordToHash = inputString;
            }
            md = MessageDigest.getInstance("SHA-512");
            return DatatypeConverter.printHexBinary(md.digest(passwordToHash.getBytes("UTF-8"))).toLowerCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            return inputString;
        }
    }

    public static String encryptString(String pin) {
        return StringEncryption.encryptString(pin, PIN_SALT);
    }
    
    public static void main(String[] args) {
	System.out.println("StringEncryption.encryptString(504924) = "+StringEncryption.encryptString("504924", null));
    }
}


