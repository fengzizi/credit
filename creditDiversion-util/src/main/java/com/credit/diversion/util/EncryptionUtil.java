package com.credit.diversion.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.*;

public class EncryptionUtil {

    //region encrypt
    private static String encrypt(String data, String charset, String encryptType) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String encoderStr = "";
        MessageDigest md5 = MessageDigest.getInstance(encryptType);
        md5.update(data.getBytes(charset));
        byte[] bytes = md5.digest();
        if (bytes != null && bytes.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            encoderStr = stringBuilder.toString();
        }
        return encoderStr;
    }
    //endregion

    //region md5
    public static String md5(String data) {
        try {
            return md5(data, "UTF-8");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String md5(String data, String charset) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return encrypt(data, charset, "MD5");
    }
    //endregion

    //region sha1
    public static String sha1(String data) {
        try {
            return sha1(data, "UTF-8");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha1(String data, String charset) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return encrypt(data, charset, "SHA1");
    }
    //endregion

    //region base64
    public static String toBase64(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    public static String toBase64(String data) throws Exception {
        return toBase64(data, "UTF-8");
    }

    public static String toBase64(String data, String charset) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] bytes = data.getBytes(charset);
        return toBase64(bytes);
    }

    public static byte[] formBase64(String data) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }
    //endregion
}
