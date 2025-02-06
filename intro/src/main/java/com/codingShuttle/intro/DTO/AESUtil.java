package com.codingShuttle.intro.DTO;


import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;





public class AESUtil {


    private static final String AES_GCM_ALGO = "AES/GCM/NoPadding";
    private static final String AES_CBC_ALGO = "AES/CBC/PKCS5PADDING";


    private static final String ALGO = "AES";


    @Value("t8WONCnwielzHCByvT79aRX64sjeokne")
    private String connectOneSMSEncKey = "t8WONCnwielzHCByvT79aRX64sjeokne";


    private String secretKey = "o590451hke31gfat8t3dnd007340s45t";


    private String dbsecretKey;


    private String iv;


    public byte[] generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] ivByte = new byte[12];
        random.nextBytes(ivByte);
        return ivByte;
    }


    ///// REQ RES ENCRYPT AND DEcecrypt
    public String encryptWithPrefixIV(String data) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] ivByte = generateIV();
        String encryptedData = encryptData(data, secretKey, ivByte);
        String ivString = Base64.getEncoder().encodeToString(ivByte);
        return ivString + encryptedData;
    }


    public String decryptWithPrefixIV(String ivPlusEncryptedData) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] decodedBytes = Base64.getDecoder().decode(ivPlusEncryptedData);
        byte[] decodedIv = new byte[12];
        System.arraycopy(decodedBytes, 0, decodedIv, 0, 12);


        byte[] encryptedData = new byte[decodedBytes.length - 12];
        System.arraycopy(decodedBytes, 12, encryptedData, 0, encryptedData.length);
        return decryptData(Base64.getEncoder().encodeToString(encryptedData), secretKey, decodedIv);
    }


    //// Req

    public String encryptData(String data, String secretKey, byte[] iv)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(AES_GCM_ALGO);
        SecretKeySpec keyspec = new SecretKeySpec(secretKey.getBytes(), ALGO);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, gcmParameterSpec);
        byte[] original = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(original);


    }


    private String decryptData(String data, String secretKey, byte[] iv)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(AES_GCM_ALGO);
        SecretKeySpec keyspec = new SecretKeySpec(secretKey.getBytes(), ALGO);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, keyspec, gcmParameterSpec);
        Decoder decoder = Base64.getDecoder();
        byte[] encrypted1 = decoder.decode(data);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, StandardCharsets.UTF_8).trim();
    }


    ////// DATA BASE VALUE ENCRT DECFRT

    public String encryptData(String data) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (data == null || data.isEmpty()) {
            return data;
        }
        return this.encryptData(data, dbsecretKey, iv.getBytes());
    }


    public String decryptData(String data) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (data == null || data.isEmpty()) {
            return data;
        }
        return this.decryptData(data, dbsecretKey, iv.getBytes());
    }


    //// SSO ENCRT DECRT
    public String cbcEncrypt(String value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String initVector = connectOneSMSEncKey.substring(0, 16);
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(connectOneSMSEncKey.getBytes(StandardCharsets.UTF_8), "AES");


        Cipher cipher = Cipher.getInstance(AES_CBC_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);


        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String cbcDecrypt(String encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String initVector = connectOneSMSEncKey.substring(0, 16);
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(connectOneSMSEncKey.getBytes(StandardCharsets.UTF_8), "AES");


        Cipher cipher = Cipher.getInstance(AES_CBC_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));


        return new String(original);
    }

    private static void setUserData() {
        String user = "[11,34,56,78]";
        System.out.println("USER :" + user);
        String[] custId = user.split(",");
        String custIdStr = Arrays.toString(custId);
        System.out.println("custID:" + custIdStr);

        String userCust = user;
        System.out.println("userCust :" + userCust);
        userCust = userCust.replace("[", "");
        userCust = userCust.replace("]", "");

        String[] userCustIds = userCust.split("\\,");
        System.out.println("userCust :" + userCustIds);
//        if (userCustIds[0].isBlank()) {
//            user.setUserType(YblConstant.APP_NTB);
//        } else {
//            user.setUserType(YblConstant.APP_ETB);
//        }
    }

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        AESUtil a = new AESUtil();
//
        String s = a.decryptWithPrefixIV("+w7AUKK0n964sBP8pU415vHgJHzS/JYp4OUCmM+vnqt9tKM52GOQxV83jkTln22HwEiWmH/sYx7Ezr+JUu6OY2q/+5JGvSh7uBq1EqY+wDYxZkcj9JzZ0jx0VkV/kWNSnzHqlX3GcwopexCtMdeptUqXUY7bucs67oFzUEkF+7Pc44eNOX7r+PthkRc9QXrB49K0DwZfLXNyfk9coVLCLGNZsNb+iH/m8UAMxZwEuS9IBRc28Big6IqMcvx0Bapr8w==");
        //  String s = a.decryptData("WD/rSWY6nbcoQj0r/LnG91HVLUoQ6m9rSxqDhy2eRQBz1i3Mqm9oH3ZbGnVomrAXPKZ3kUssN4y5fZ4WspfnZEwglIczD+c3SBX4iprRmMXwakigNeycWz85/QdzrzCJ0B3cWaTBipFYD+94XQLu2iLf45bJqYLKiAe1mkjK9meT1r9KBTEjRr1XyeA8z9Zbnq9F");
        //     String s = a.cbcDecrypt("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");
//        System.out.println("Response");
        System.out.println(s);
        setUserData();
        String e = a.encryptWithPrefixIV("$T+2F7pXaaev7!dC");
        System.out.println("ujjwal");
        System.out.println(e);

    }

}

//    select distinct p.product_id as productId, p.product_name as productName, p.data_transfer_method as dataTransferMethod,solution_name as solutionName, o.redirect_url as redirectUrl, o.landing_page_url as landingPageUrl from product p left join(select product_id, solution_id from (\n"
//            + "select rel.product_id, rel.solution_id, ROW_NUMBER() over (partition by rel.product_id order by sol.solution_name) AS RowNumber\n"
//            + "from product_solution_rel rel \n"
//            + "join solution sol on sol.solution_id = rel.solution_id ) as r1\n"
//            + "where RowNumber = 1) ps on p.product_id=ps.product_id left join solution s on ps.solution_id = s.solution_id left join overview o on p.product_id = o.product_id where o.solution_tag is null and p.is_active=1;", nativeQuery = true)