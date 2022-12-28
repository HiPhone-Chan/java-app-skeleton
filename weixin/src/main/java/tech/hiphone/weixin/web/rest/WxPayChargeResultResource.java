package tech.hiphone.weixin.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.weixin.domain.WxPayChargeResult;
import tech.hiphone.weixin.repository.WxPayChargeResultRepository;
import tech.hiphone.weixin.web.vm.WxPayChargeResultVM;

@RestController
public class WxPayChargeResultResource {

    @Autowired
    private WxPayChargeResultRepository wxPayChargeResultRepository;

    @GetMapping("/api/wx/pay/charge/results")
    @Secured({ AuthoritiesConstants.MANAGER })
    public Page<WxPayChargeResultVM> getWxPayChargeResults(Pageable pageable,
            @RequestParam(required = false) String search) {
        Specification<WxPayChargeResult> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> andList = new ArrayList<>();
            if (StringUtils.isNotEmpty(search)) {
                List<Predicate> orList = new ArrayList<>();

                String like = "%" + search + "%";

                orList.add(criteriaBuilder.like(root.get("resource"), like));
                andList.add(criteriaBuilder.or(orList.toArray(new Predicate[orList.size()])));
            }

            query.where(criteriaBuilder.and(andList.toArray(new Predicate[andList.size()])));
            return query.getRestriction();
        };
        return wxPayChargeResultRepository.findAll(spec, pageable).map(WxPayChargeResultVM::new);
    }
}
