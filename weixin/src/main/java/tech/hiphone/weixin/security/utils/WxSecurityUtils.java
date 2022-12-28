package tech.hiphone.weixin.security.utils;

import java.util.Optional;

import tech.hiphone.commons.security.SecurityUtils;
import tech.hiphone.weixin.domain.id.WxUserId;

public class WxSecurityUtils {

    private WxSecurityUtils() {
    }

    public static Optional<WxUserId> getCurrentWxUserId() {
        return SecurityUtils.getCurrentUserLogin().map(subject -> {
            String[] splits = subject.split(",");
            if (splits != null && splits.length > 1) {
                return new WxUserId(splits[0], splits[1]);
            }
            return new WxUserId();
        });
    }

}
