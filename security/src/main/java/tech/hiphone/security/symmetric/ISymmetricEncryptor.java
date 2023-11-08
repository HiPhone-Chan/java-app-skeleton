package tech.hiphone.security.symmetric;

public interface ISymmetricEncryptor {

    /**
     * 加密
     * @param plainText 明文字符串
     * @param aad 增强加密的随机串
     * @return 密文
     */
    byte[] encrypt(String plainText, byte[] params, byte[] aad) throws Exception;

    /**
     * 解密
     * @param cipherText 密文
     * @param aad 解密的随机串
     * @return 明文
     */
    byte[] decrypt(String cipherText, byte[] params, byte[] aad) throws Exception;

}
