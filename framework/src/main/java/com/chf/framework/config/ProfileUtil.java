package com.chf.framework.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

public final class ProfileUtil {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private ProfileUtil() {
    }

    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(SPRING_PROFILE_DEFAULT, ProfileConstants.SPRING_PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
    }

}
