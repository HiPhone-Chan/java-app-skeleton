package tech.hiphone.security.symmetric;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import tech.hiphone.security.utils.SecurityConstant;

public abstract class AbstractEncryptor implements ISymmetricEncryptor {

    protected final String charset = SecurityConstant.CHARSET;

    protected final String transformation;

    protected final String algorithm;

    protected final SecretKeySpec secretKeySpec;

    public AbstractEncryptor(String transformation, String algorithm, String keySpec) throws Exception {
        this(transformation, algorithm, keySpec.getBytes());
    }

    public AbstractEncryptor(String transformation, String algorithm, byte[] keySpec) throws Exception {
        this.transformation = transformation;
        this.algorithm = algorithm;
        this.secretKeySpec = new SecretKeySpec(keySpec, algorithm);
    }

    public String encrypt(String plainText, String params, String aad) throws Exception {
        return Base64.getEncoder()
                .encodeToString(encrypt(plainText, params.getBytes(), aad == null ? null : aad.getBytes()));
    }

    public String decrypt(String cipherText, String params, String aad) throws Exception {
        return new String(decrypt(cipherText, params.getBytes(), aad == null ? null : aad.getBytes()), charset);
    }

    abstract protected AlgorithmParameterSpec getAlgorithmParameterSpec(byte[] params);
}
