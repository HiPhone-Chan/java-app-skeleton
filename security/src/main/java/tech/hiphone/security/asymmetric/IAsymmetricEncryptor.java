package tech.hiphone.security.asymmetric;

public interface IAsymmetricEncryptor {
    /**
     * 加密
     * @param plainText 明文字符串
     * @return 密文
     */
    String encrypt(String plainText) throws Exception;

    /**
     * 解密
     * @param cipherText 密文
     * @return 明文
     */
    String decrypt(String cipherText) throws Exception;

    /**
     * 对内容进行签名
     * @param content 待签名的原文
     * @param privateKey 私钥
     * @return 签名字符串
     */
    String sign(String content) throws Exception;

    /**
     * 验证签名
     * @param content 待校验的原文
     * @param publicKey 公钥
     * @param sign 签名字符串
     * @return true：通过
     */
    boolean verify(String content, String sign) throws Exception;

}
