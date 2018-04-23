package com.ruking.frame.library.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des {

    /**
     * DES解密
     *
     * @param message 密文
     * @param key     密钥
     * @return
     */
    public String decrypt(String message, byte[] key) {
        try {
            byte[] bytesrc = Base64.decode(message);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            String str = new String(retByte, "utf-8");
            return str;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * DES加密
     *
     * @param message 要加密的数据
     * @param key     密钥
     * @return
     */
    public byte[] encryptToByte(String message, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * DES加密
     *
     * @param message 要加密的数据
     * @param key     密钥
     * @return
     */
    public String encryptToString(String message, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
            return Base64.encode(bytes);
        } catch (Exception e) {
        }
        return null;
    }

}