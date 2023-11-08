package tech.hiphone.commons.service.helper;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import tech.hiphone.framework.jpa.support.JpaExtRepository;

// 运行任务
public class RunTaskHelper<T, ID> {

    private static final Logger log = LoggerFactory.getLogger(RunTaskHelper.class);

    private final int pageSize;

    private final Specification<T> spec;

    private final JpaExtRepository<T, ID> repository;

    public RunTaskHelper(Specification<T> spec, JpaExtRepository<T, ID> repository) {
        this.pageSize = 100;
        this.spec = spec;
        this.repository = repository;
    }

    /**
     * 
     * @param func true有修改查询状态
     */
    public void run(Function<T, Boolean> func) {
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<T> page = Page.empty();

        do {
            page = repository.findAll(spec, pageable);
            List<T> resultList = page.getContent();

            int failCount = 0;
            boolean stateChange = false;
            for (T item : resultList) {
                try {
                    stateChange = func.apply(item);
                } catch (Exception e) {
                    log.warn("Run task fail", e);
                    stateChange = false;
                }
            }

            if (!stateChange) {
                failCount++;
            }

            if (failCount == pageSize) { // 整页全部失败
                pageable = page.nextPageable();
            }
        } while (page.hasNext());
    }

}
