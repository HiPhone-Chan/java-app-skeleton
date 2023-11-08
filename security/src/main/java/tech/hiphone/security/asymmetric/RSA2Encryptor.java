package tech.hiphone.security.asymmetric;

public class RSA2Encryptor extends RSAEncryptor {

    public RSA2Encryptor(String privateKey, String publicKey) throws Exception {
        super("SHA256WithRSA", privateKey, publicKey);
    }

    // RSA2最大解密密文大小(2048/8=256)
    @Override
    protected int getMaxDecryptBlockSize() {
        return 256;
    }

    // RSA2最大加密明文大小(2048/8-11=244)
    @Override
    protected int getMaxEncryptBlockSize() {
        return 244;
    }
}
