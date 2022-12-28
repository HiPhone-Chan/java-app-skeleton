package tech.hiphone.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cms", ignoreUnknownFields = false)
public class CmsProperties {

    private final BasePath basePath = new BasePath();

    private final ContentCheck contentCheck = new ContentCheck();

    public BasePath getBasePath() {
        return basePath;
    }

    public ContentCheck getContentCheck() {
        return contentCheck;
    }

    public static class BasePath {

        private String local;

        private String web;

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }

        public String getWeb() {
            return web;
        }

        public void setWeb(String web) {
            this.web = web;
        }
    }

    public static class ContentCheck {

        private boolean needCheck;

        public boolean isNeedCheck() {
            return needCheck;
        }

        public void setNeedCheck(boolean needCheck) {
            this.needCheck = needCheck;
        }

    }
}
