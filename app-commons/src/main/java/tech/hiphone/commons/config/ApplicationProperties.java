package tech.hiphone.commons.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final AccountProperties account = new AccountProperties();

    public AccountProperties getAccount() {
        return account;
    }

}
