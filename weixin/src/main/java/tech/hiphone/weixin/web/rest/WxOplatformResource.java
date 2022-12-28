package tech.hiphone.weixin.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.weixin.domain.WxOplatform;
import tech.hiphone.weixin.repository.WxOplatformRepository;

@RestController
public class WxOplatformResource {

    private final WxOplatformRepository wxOplatformRepository;

    public WxOplatformResource(WxOplatformRepository wxOplatformRepository) {
        this.wxOplatformRepository = wxOplatformRepository;
    }

    @PostMapping("/api/wx/oplatform")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void createWxOplatform(@Valid @RequestBody WxOplatform wxOplatformVM) throws URISyntaxException {
        boolean result = wxOplatformRepository.existsById(wxOplatformVM.getAppId());
        if (result == true) {
            throw new ServiceException(ErrorCodeContants.DATA_EXISTS);
        }
        wxOplatformRepository.save(wxOplatformVM);
    }

    @PutMapping("/api/wx/oplatform")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void updateWxOplatform(@Valid @RequestBody WxOplatform wxOplatformVM) {
        WxOplatform wxOplatform = wxOplatformRepository.findById(wxOplatformVM.getAppId())
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
        WxOplatform newWxOplatform = wxOplatformVM.clone();
        newWxOplatform.setAppId(wxOplatformVM.getAppId());
        if (StringUtils.isEmpty(wxOplatformVM.getAppSecret())) {
            newWxOplatform.setAppSecret(wxOplatform.getAppSecret());
        }
        wxOplatformRepository.save(newWxOplatform);

    }

    @GetMapping("/api/wx/oplatforms")
    @Secured({ AuthoritiesConstants.ADMIN })
    public Page<WxOplatform> getWxOplatforms(Pageable pageable,
            @RequestParam(name = "search", required = false) String search) {
        Specification<WxOplatform> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> andList = new ArrayList<>();

            if (StringUtils.isNotEmpty(search)) {
                List<Predicate> orList = new ArrayList<>();
                String like = "%" + search + "%";

                orList.add(criteriaBuilder.like(root.get("appId"), like));
                orList.add(criteriaBuilder.like(root.get("appType"), like));
                orList.add(criteriaBuilder.like(root.get("name"), like));
                orList.add(criteriaBuilder.like(root.get("description"), like));

                andList.add(criteriaBuilder.or(orList.toArray(new Predicate[orList.size()])));
            }

            query.where(criteriaBuilder.and(andList.toArray(new Predicate[andList.size()])));
            return query.getRestriction();
        };
        return wxOplatformRepository.findAll(spec, pageable).map(wxOplatform -> {
            wxOplatform.setAppSecret(null);
            return wxOplatform;
        });
    }

    @GetMapping("/api/wx/oplatform/check/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public boolean checkWxOplatformAppId(@PathVariable String appId) {
        return wxOplatformRepository.existsById(appId);
    }

    @GetMapping("/api/wx/oplatform")
    public WxOplatform getWxOplatform(@RequestParam String appId) {
        return wxOplatformRepository.findById(appId)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
    }

    @DeleteMapping("/api/wx/oplatform/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void deleteWxOplatform(@PathVariable("appId") String appId) {
        wxOplatformRepository.deleteById(appId);
    }

}
