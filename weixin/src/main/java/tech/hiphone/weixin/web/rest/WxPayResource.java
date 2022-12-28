package tech.hiphone.weixin.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import tech.hiphone.weixin.domain.WxPay;
import tech.hiphone.weixin.repository.WxPayRepository;

@RestController
public class WxPayResource {

    @Autowired
    private WxPayRepository wxPayRepository;

    @PostMapping("/api/wx/pay")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void createWxPay(@Valid @RequestBody WxPay wxPayVM) throws URISyntaxException {
        boolean result = wxPayRepository.existsById(wxPayVM.getAppId());
        if (result == true) {
            throw new ServiceException(ErrorCodeContants.DATA_EXISTS);
        }
        wxPayRepository.save(wxPayVM);
    }

    @PutMapping("/api/wx/pay")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void updateWxPay(@Valid @RequestBody WxPay wxPayVM) {
        WxPay wxPay = wxPayRepository.findById(wxPayVM.getAppId())
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
        WxPay newWxPay = wxPayVM.clone();
        newWxPay.setAppId(wxPayVM.getAppId());
        if (StringUtils.isEmpty(wxPayVM.getApiV3Secret())) {
            newWxPay.setApiV3Secret(wxPay.getApiV3Secret());
        }
        wxPayRepository.save(newWxPay);

    }

    @GetMapping("/api/wx/pays")
    @Secured({ AuthoritiesConstants.ADMIN })
    public Page<WxPay> getWxPays(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
        Specification<WxPay> spec = (root, query, criteriaBuilder) -> {
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
        return wxPayRepository.findAll(spec, pageable).map(wxPay -> {
            wxPay.setApiV3Secret(null);
            return wxPay;
        });
    }

    @GetMapping("/api/wx/pay/check/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public boolean checkWxPayAppId(@PathVariable String appId) {
        return wxPayRepository.existsById(appId);
    }

    @GetMapping("/api/wx/pay")
    public WxPay getWxPay(@RequestParam String appId) {
        return wxPayRepository.findById(appId).orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
    }

    @DeleteMapping("/api/wx/pay/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void deleteWxPay(@PathVariable("appId") String appId) {
        wxPayRepository.deleteById(appId);
    }

}
