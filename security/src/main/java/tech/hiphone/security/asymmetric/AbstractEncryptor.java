package tech.hiphone.security.asymmetric;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import tech.hiphone.security.utils.SecurityConstant;

public abstract class AbstractEncryptor implements IAsymmetricEncryptor {

    protected final String charset = SecurityConstant.CHARSET;

    protected final String transformation;

    // 签名使用的算法
    protected final String signAlgorithm;
    // 读取key时使用的算法
    protected final String keyAlgorithm;

    protected final PrivateKey privateKey;
    // key or certificate 二选一
    protected final PublicKey publicKey;

    protected final Certificate certificate;

    public AbstractEncryptor(String transformation, String signAlgorithm, String keyAlgorithm, String privateKey,
            String publicKey) throws Exception {
        this.transformation = transformation;
        this.signAlgorithm = signAlgorithm;
        this.keyAlgorithm = keyAlgorithm;
        this.privateKey = (privateKey == null ? null : getPrivateKey(privateKey.getBytes()));
        this.publicKey = (publicKey == null ? null : getPublicKey(publicKey.getBytes()));
        this.certificate = null;
    }

    public AbstractEncryptor(String transformation, String signAlgorithm, String keyAlgorithm, String privateKey,
            Certificate certificate) throws Exception {
        this.transformation = transformation;
        this.signAlgorithm = signAlgorithm;
        this.keyAlgorithm = keyAlgorithm;
        this.privateKey = (privateKey == null ? null : getPrivateKey(privateKey.getBytes()));
        this.publicKey = null;
        this.certificate = certificate;
    }

    abstract protected PrivateKey getPrivateKey(byte[] privateKey) throws Exception;

    abstract protected PublicKey getPublicKey(byte[] publicKey) throws Exception;
}
