package com.kenoly.udemy.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {
    private final Random RANODM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";


    public String generateKey(int length){
        return generatedRandomString(length);
    }
    
    public String generateAddressId(int length){
        return generatedRandomString(length);
    }

    private String generatedRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for(int i =0; i<length; i++){
            returnValue.append(ALPHABET.charAt(RANODM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}
