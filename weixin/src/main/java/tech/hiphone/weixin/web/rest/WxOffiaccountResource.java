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
import tech.hiphone.weixin.domain.WxOffiaccount;
import tech.hiphone.weixin.repository.WxOffiaccountRepository;

@RestController
public class WxOffiaccountResource {

    private final WxOffiaccountRepository wxOffiaccountRepository;

    public WxOffiaccountResource(WxOffiaccountRepository wxOffiaccountRepository) {
        this.wxOffiaccountRepository = wxOffiaccountRepository;
    }

    @PostMapping("/api/wx/offiaccount")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void createWxOffiaccount(@Valid @RequestBody WxOffiaccount wxOffiaccountVM) throws URISyntaxException {
        boolean result = wxOffiaccountRepository.existsById(wxOffiaccountVM.getAppId());
        if (result == true) {
            throw new ServiceException(ErrorCodeContants.DATA_EXISTS);
        }
        wxOffiaccountRepository.save(wxOffiaccountVM);
    }

    @PutMapping("/api/wx/offiaccount")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void updateWxOffiaccount(@Valid @RequestBody WxOffiaccount wxOffiaccountVM) {
        WxOffiaccount wxOffiaccount = wxOffiaccountRepository.findById(wxOffiaccountVM.getAppId())
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
        WxOffiaccount newWxOffiaccount = wxOffiaccountVM.clone();
        newWxOffiaccount.setAppId(wxOffiaccountVM.getAppId());
        if (StringUtils.isEmpty(wxOffiaccountVM.getAppSecret())) {
            newWxOffiaccount.setAppSecret(wxOffiaccount.getAppSecret());
        }
        if (StringUtils.isEmpty(wxOffiaccountVM.getEncodingAesKey())) {
            newWxOffiaccount.setEncodingAesKey(wxOffiaccount.getEncodingAesKey());
        }
        wxOffiaccountRepository.save(newWxOffiaccount);

    }

    @GetMapping("/api/wx/offiaccounts")
    @Secured({ AuthoritiesConstants.ADMIN })
    public Page<WxOffiaccount> getWxOffiaccounts(Pageable pageable,
            @RequestParam(name = "search", required = false) String search) {
        Specification<WxOffiaccount> spec = (root, query, criteriaBuilder) -> {
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
        return wxOffiaccountRepository.findAll(spec, pageable).map(wxOffiaccount -> {
            wxOffiaccount.setAppSecret(null);
            wxOffiaccount.setEncodingAesKey(null);
            return wxOffiaccount;
        });
    }

    @GetMapping("/api/wx/offiaccount/check/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public boolean checkWxOffiaccountAppId(@PathVariable String appId) {
        return wxOffiaccountRepository.existsById(appId);
    }

    @GetMapping("/api/wx/offiaccount")
    public WxOffiaccount getWxOffiaccount(@RequestParam String appId) {
        return wxOffiaccountRepository.findById(appId)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
    }

    @DeleteMapping("/api/wx/offiaccount/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void deleteWxOffiaccount(@PathVariable("appId") String appId) {
        wxOffiaccountRepository.deleteById(appId);
    }

}
