package tech.hiphone.cms.constants;

import java.util.Arrays;
import java.util.List;

// 内容安全检查状态
public interface ContentCheckStatus {
    /**
     * 暂不检查
     */
    int NO_CHECK = -2;
    /**
    * 检查失败
    */
    int FAILED = -1;
    /**
     * 安全
     */
    int SAFE = 0;
    /**
     * 等待检查
     */
    int WAITING = 1;
    /**
     * 检查中, 等待结果
     */
    int CHECKING = 2;

    List<Integer> SAFE_LIST = Arrays.asList(NO_CHECK, SAFE);

}
