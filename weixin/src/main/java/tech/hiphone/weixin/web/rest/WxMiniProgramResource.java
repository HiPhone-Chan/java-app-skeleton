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
import tech.hiphone.weixin.domain.WxMiniProgram;
import tech.hiphone.weixin.repository.WxMiniProgramRepository;

@RestController
public class WxMiniProgramResource {

    @Autowired
    private WxMiniProgramRepository wxMiniProgramRepository;

    @PostMapping("/api/wx/mini-program")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void createWxMiniProgram(@Valid @RequestBody WxMiniProgram wxMiniProgramVM) throws URISyntaxException {
        boolean result = wxMiniProgramRepository.existsById(wxMiniProgramVM.getAppId());
        if (result == true) {
            throw new ServiceException(ErrorCodeContants.DATA_EXISTS);
        }
        wxMiniProgramRepository.save(wxMiniProgramVM);
    }

    @PutMapping("/api/wx/mini-program")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void updateWxMiniProgram(@Valid @RequestBody WxMiniProgram wxMiniProgramVM) {
        WxMiniProgram wxMiniProgram = wxMiniProgramRepository.findById(wxMiniProgramVM.getAppId())
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
        WxMiniProgram newWxMiniProgram = wxMiniProgramVM.clone();
        newWxMiniProgram.setAppId(wxMiniProgramVM.getAppId());
        if (StringUtils.isEmpty(wxMiniProgramVM.getAppSecret())) {
            newWxMiniProgram.setAppSecret(wxMiniProgram.getAppSecret());
        }
        if (StringUtils.isEmpty(wxMiniProgramVM.getEncodingAesKey())) {
            newWxMiniProgram.setEncodingAesKey(wxMiniProgram.getEncodingAesKey());
        }
        wxMiniProgramRepository.save(newWxMiniProgram);

    }

    @GetMapping("/api/wx/mini-programs")
    @Secured({ AuthoritiesConstants.ADMIN })
    public Page<WxMiniProgram> getWxMiniPrograms(Pageable pageable,
            @RequestParam(name = "search", required = false) String search) {
        Specification<WxMiniProgram> spec = (root, query, criteriaBuilder) -> {
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
        return wxMiniProgramRepository.findAll(spec, pageable).map(wxMiniProgram -> {
            wxMiniProgram.setAppSecret(null);
            wxMiniProgram.setEncodingAesKey(null);
            return wxMiniProgram;
        });
    }

    @GetMapping("/api/wx/mini-program/check/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public boolean checkWxMiniProgramAppId(@PathVariable String appId) {
        return wxMiniProgramRepository.existsById(appId);
    }

    @GetMapping("/api/wx/mini-program")
    public WxMiniProgram getWxMiniProgram(@RequestParam String appId) {
        return wxMiniProgramRepository.findById(appId)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA));
    }

    @DeleteMapping("/api/wx/mini-program/{appId}")
    @Secured({ AuthoritiesConstants.ADMIN })
    public void deleteWxMiniProgram(@PathVariable("appId") String appId) {
        wxMiniProgramRepository.deleteById(appId);
    }

}
