package tech.hiphone.security.digest;

public interface IMessageDigest {

    /**
     * 对内容进行消息摘要
     * @param content 原文
     * @return 消息摘要
     */
    String encode(String data);
}
