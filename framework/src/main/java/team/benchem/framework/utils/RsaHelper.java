package team.benchem.framework.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaHelper {

    public static final String CHAR_SET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    public static final int KEY_SIZE = 1024;
    public static final String PUBLICKEY_NAME = "publicKey";
    public static final String PRIVATE_NAME = "privateKey";

    /**
     * 创建RSA密钥对
     * Size 默认1024
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> createKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keypair = keyPairGenerator.generateKeyPair();
        Map<String, String> keyPairMap = new HashMap<String, String>();

        keyPairMap.put(PUBLICKEY_NAME, Base64.encodeBase64URLSafeString(keypair.getPublic().getEncoded()));
        keyPairMap.put(PRIVATE_NAME, Base64.encodeBase64URLSafeString(keypair.getPrivate().getEncoded()));
        return keyPairMap;
    }

    /**
     * 公钥加密
     *
     * @param data         加密内容
     * @param publicKeyStr 公钥字符串
     * @return 加密后BASE64编码的字符串
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String publicKeyEncrypt(String data, String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] dataBytes = data.getBytes();
        RSAPublicKey rsaPublicKey = transToPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return Base64.encodeBase64URLSafeString(cipher.doFinal(dataBytes));
    }


    /**
     * 公钥解密
     *
     * @param data        加密后BASE64编码的字符串
     * @param publikeyStr 公钥字符串
     * @return 加密前字符串
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String publicKeyDecrypt(String data, String publikeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] dataBytes = Base64.decodeBase64(data.getBytes());
        RSAPublicKey rsaPublicKey = transToPublicKey(publikeyStr);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        return new String(cipher.doFinal(dataBytes));
    }


    /**
     * 私钥解密
     *
     * @param data       加密后BASE64编码的字符串
     * @param privateStr 私钥字符串
     * @return 加密前字符串
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String privateKeyDecrypt(String data, String privateStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] dataBytes = Base64.decodeBase64(data.getBytes());
        RSAPrivateKey rsaPrivateKey = transToPrivateKey(privateStr);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return new String(cipher.doFinal(dataBytes));
    }

    /**
     * 私钥加密
     *
     * @param data       加密内容
     * @param privateStr 私钥字符串
     * @return 加密后BASE64编码的字符串
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String privateKeyEncrypt(String data, String privateStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] dataBytes = data.getBytes();
        RSAPrivateKey rsaPrivateKey = transToPrivateKey(privateStr);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        return Base64.encodeBase64URLSafeString(cipher.doFinal(dataBytes));
    }

    /**
     * 转换私钥字符串
     *
     * @param privateKeyStr BASE64编码后的私钥字符串
     * @return 私钥对象
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static RSAPrivateKey transToPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        // 通过PKCS#8编码的Key指令获取私钥对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * 转换公钥字符串
     *
     * @param publicKeyStr BASE64编码后的公钥字符串
     * @return 公钥对象
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static RSAPublicKey transToPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        // 通过X509编码的Key指令获得公钥对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
    }
}
