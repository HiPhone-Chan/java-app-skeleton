package tech.hiphone.security.asymmetric;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class RSAEncryptor extends AbstractEncryptor implements IAsymmetricEncryptor {

    public RSAEncryptor(String privateKey, String publicKey) throws Exception {
        this("SHA1WithRSA", privateKey, publicKey);
    }

    public RSAEncryptor(String signAlgorithm, String privateKey, String publicKey) throws Exception {
        this("RSA", signAlgorithm, privateKey, publicKey);
    }

    public RSAEncryptor(String transformation, String signAlgorithm, String privateKey, String publicKey)
            throws Exception {
        super(transformation, signAlgorithm, "RSA", privateKey, publicKey);
    }

    public RSAEncryptor(String transformation, String signAlgorithm, String privateKey, Certificate certificate)
            throws Exception {
        super(transformation, signAlgorithm, "RSA", privateKey, certificate);
    }

    @Override
    public String encrypt(String plainText) throws Exception {
        int maxEncrypt = getMaxEncryptBlockSize();
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = plainText.getBytes(charset);
        int inputLen = data.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > maxEncrypt) {
                    cache = cipher.doFinal(data, offSet, maxEncrypt);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * maxEncrypt;
            }
            byte[] encryptedData = Base64.getEncoder().encode(out.toByteArray());
            return new String(encryptedData);
        }
    }

    @Override
    public String decrypt(String cipherTextBase64) throws Exception {
        int maxDecrypt = getMaxDecryptBlockSize();
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedData = Base64.getDecoder().decode(cipherTextBase64.getBytes());
        int inputLen = encryptedData.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > maxDecrypt) {
                    cache = cipher.doFinal(encryptedData, offSet, maxDecrypt);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * maxDecrypt;
            }
            byte[] decryptedData = out.toByteArray();
            return new String(decryptedData, charset);
        }
    }

    @Override
    public String sign(String content) throws Exception {
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(content.getBytes(charset));

        byte[] signed = signature.sign();

        return new String(Base64.getEncoder().encode(signed));
    }

    @Override
    public boolean verify(String content, String sign) throws Exception {
        Signature signature = Signature.getInstance(signAlgorithm);

        if (publicKey == null) {
            signature.initVerify(certificate);
        } else {
            signature.initVerify(publicKey);
        }
        signature.update(content.getBytes(charset));

        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

    // RSA最大加密明文大小(1024/8-11=117)
    protected int getMaxEncryptBlockSize() {
        return 117;
    }

    // RSA最大解密密文大小(1024/8=128)
    protected int getMaxDecryptBlockSize() {
        return 128;
    }

    public PrivateKey getPrivateKey(byte[] privateKey) throws Exception {
        if (ArrayUtils.isEmpty(privateKey) || StringUtils.isEmpty(keyAlgorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        byte[] encodedKey = Base64.getDecoder().decode(privateKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public PublicKey getPublicKey(byte[] publicKey) throws Exception {
        if (ArrayUtils.isEmpty(publicKey) || StringUtils.isEmpty(keyAlgorithm)) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);

        byte[] encodedKey = Base64.getDecoder().decode(publicKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

}
