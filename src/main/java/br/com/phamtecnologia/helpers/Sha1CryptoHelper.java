package br.com.phamtecnologia.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1CryptoHelper {

    public static String get(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(value.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao calcular hash SHA-1", e);
        }
    }
}