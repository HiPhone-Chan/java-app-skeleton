package tech.hiphone.commons.config;

import tech.hiphone.commons.constants.CodeKeyType;

public interface ApplicationDefaults {

    String keyType = CodeKeyType.NUMBER;

    int keyValidityInSeconds = 300;
}
