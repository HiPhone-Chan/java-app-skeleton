package tech.hiphone.cms.domain.common;

import java.util.Collections;
import java.util.List;

// 内容检查接口
public interface IContentCheck {

    Integer getContentCheckStatus();

    void setContentCheckStatus(Integer contentCheckStatus);

    default List<String> getContentCheckIds() {
        return Collections.emptyList();
    }

    default void setContentCheckIds(List<String> contentCheckIds) {
    }

}
