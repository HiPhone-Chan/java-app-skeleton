package tech.hiphone.security.symmetric;

import java.util.Base64;

import javax.crypto.Cipher;

/**
 * 模式
 * 填充方式
 * 模式为CBC时初始向量
 * 密钥
 * @author chhfeng
 *
 */
public abstract class AESEncryptor extends AbstractEncryptor {

    public AESEncryptor(String transformation, String keySpec) throws Exception {
        super(transformation, "AES", keySpec);
    }

    public AESEncryptor(String transformation, byte[] keySpec) throws Exception {
        super(transformation, "AES", keySpec);
    }

    @Override
    public byte[] encrypt(String plainText, byte[] params, byte[] aad) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, getAlgorithmParameterSpec(params));
            if (aad != null) {
                cipher.updateAAD(aad);
            }

            return cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            throw new IllegalArgumentException("加密失败");
        }
    }

    @Override
    public byte[] decrypt(String base64Ciphertext, byte[] params, byte[] aad) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(transformation);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, getAlgorithmParameterSpec(params));
            if (aad != null) {
                cipher.updateAAD(aad);
            }
            return cipher.doFinal(Base64.getDecoder().decode(base64Ciphertext));
        } catch (Exception e) {
            throw new IllegalArgumentException("解密失败");
        }
    }

}
