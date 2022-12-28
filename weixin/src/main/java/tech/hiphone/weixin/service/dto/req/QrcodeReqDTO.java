package tech.hiphone.weixin.service.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QrcodeReqDTO {
    // 临时 整型 使用scene_id
    public final static String QR_SCENE = "QR_SCENE";
    // 临时 字符串 使用scene_str
    public final static String QR_STR_SCENE = "QR_STR_SCENE";
    // 永久 整型 使用scene_id
    public final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
    // 永久 字符串 使用scene_str
    public final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";

    @JsonProperty("expire_seconds")
    private Long expireSeconds;
    @JsonProperty("action_name")
    private String actionName;
    @JsonProperty("action_info")
    private ActionInfo actionInfo = new ActionInfo();

    public Long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public void setSceneId(String sceneId) {
        this.actionInfo.getScene().setSceneId(sceneId);
    }

    public void setSceneStr(String sceneStr) {
        this.actionInfo.getScene().setSceneStr(sceneStr);
    }

    public static class ActionInfo {

        @JsonProperty("scene")
        private Scene scene = new Scene();

        public Scene getScene() {
            return scene;
        }

        public void setScene(Scene scene) {
            this.scene = scene;
        }

    }

    public static class Scene {
        @JsonProperty("scene_id")
        private String sceneId;
        @JsonProperty("scene_str")
        private String sceneStr;

        public String getSceneId() {
            return sceneId;
        }

        public void setSceneId(String sceneId) {
            this.sceneId = sceneId;
        }

        public String getSceneStr() {
            return sceneStr;
        }

        public void setSceneStr(String sceneStr) {
            this.sceneStr = sceneStr;
        }
    }

}
