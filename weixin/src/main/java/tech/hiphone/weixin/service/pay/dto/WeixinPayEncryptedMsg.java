package tech.hiphone.weixin.service.pay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// 加密报文格式
public class WeixinPayEncryptedMsg {
    // 加密前的对象类型
    @JsonProperty("original_type")
    private String originalType;
    // 加密算法, AEAD_AES_256_GCM
    @JsonProperty("algorithm")
    private String algorithm;
    // Base64编码后的密文
    @JsonProperty("ciphertext")
    private String ciphertext;
    // 加密使用的随机串初始化向量
    @JsonProperty("nonce")
    private String nonce;
    // 附加数据包（可能为空）
    @JsonProperty("associated_data")
    private String associatedData;

    public String getOriginalType() {
        return originalType;
    }

    public void setOriginalType(String originalType) {
        this.originalType = originalType;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getAssociatedData() {
        return associatedData;
    }

    public void setAssociatedData(String associatedData) {
        this.associatedData = associatedData;
    }

}
