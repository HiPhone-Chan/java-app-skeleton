package tech.hiphone.weixin.service.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import tech.hiphone.cms.constants.ContentCheckStatus;
import tech.hiphone.cms.constants.MaterialInfoType;
import tech.hiphone.cms.constants.MaterialType;
import tech.hiphone.cms.domain.common.IContentCheck;
import tech.hiphone.cms.domain.common.ITargetMaterial;
import tech.hiphone.cms.domain.common.MaterialInfo;
import tech.hiphone.cms.domain.id.TargetMaterialExtId;
import tech.hiphone.cms.repository.TargetMaterialRepository;
import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.weixin.config.WeixinProperties;
import tech.hiphone.weixin.domain.WxMediaCheckRecord;
import tech.hiphone.weixin.domain.id.MediaCheckId;
import tech.hiphone.weixin.repository.WxMediaCheckRecordRepository;
import tech.hiphone.weixin.repository.WxMediaCheckResultRepository;
import tech.hiphone.weixin.service.WeixinService;

public class ContentCheckServiceHelper<T extends IContentCheck, TID, TM extends ITargetMaterial<T, TID> & IContentCheck, TR extends JpaExtRepository<T, TID>, TMR extends JpaExtRepository<TM, TargetMaterialExtId<T>> & TargetMaterialRepository<TM, T>> {

    private static final Logger log = LoggerFactory.getLogger(ContentCheckServiceHelper.class);

    @Autowired
    private WeixinProperties weixinProperties;

    @Autowired
    private WxMediaCheckRecordRepository wxMediaCheckRecordRepository;

    @Autowired
    private WxMediaCheckResultRepository wxMediaCheckResultRepository;

    @Autowired
    private WeixinService weixinService;

    private TR targetRepository;

    private TMR targetMaterialRepository;

    private String appType;

    public ContentCheckServiceHelper(TR targetRepository, TMR targetMaterialRepository, String appType) {
        this.targetRepository = targetRepository;
        this.targetMaterialRepository = targetMaterialRepository;
        this.appType = appType;
    }

    public Boolean isContentIdRisk(List<String> traceIds) {
        String appId = weixinProperties.getOffiaccount(appType).getAppId();
        List<MediaCheckId> ids = traceIds.stream().map(traceId -> new MediaCheckId(appId, traceId))
                .collect(Collectors.toList());
        List<WxMediaCheckRecord> wxMediaCheckRecordList = wxMediaCheckRecordRepository.findAllById(ids);
        for (WxMediaCheckRecord record : wxMediaCheckRecordList) {
            Boolean isRiskResult = wxMediaCheckResultRepository.findById(record.getId()).map(result -> {
                return Optional.ofNullable(result.getStatusCode()).map(statusCode -> {
                    if (statusCode == WeixinService.SUCCESS) {
                        return Optional.ofNullable(result.getIsRisky())
                                .map(isRisk -> isRisk != WxMediaCheckRecord.NO_RISK).orElse(null);
                    }
                    return null;
                }).orElse(null);
            }).orElse(null);
            if (isRiskResult != null) {
                return isRiskResult;
            }
        }
        return null;
    }

    // 检查素材内容
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkTargetsContent(Specification<T> spec) {
        Pageable pageable = PageRequest.of(0, 100);
        Page<T> resultPage = Page.empty();
        do {
            resultPage = targetRepository.findAll(spec, pageable);
            for (T target : resultPage) {
                this.checkTargetContent(target, appType);
            }
            pageable = resultPage.nextPageable();
        } while (resultPage.hasNext());
    }

    // 检查素材内容结果
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkTargetsContentResult(Specification<T> spec) {
        Pageable pageable = PageRequest.of(0, 100);
        Page<T> resultPage = Page.empty();
        do {
            resultPage = targetRepository.findAll(spec, pageable);
            for (T target : resultPage) {
                this.checkTargetContentResult(target);
            }
            pageable = resultPage.nextPageable();
        } while (resultPage.hasNext());
    }

    // T 目标 TM 目标素材
    public void checkTargetContent(T target, String appType) {
        String appId = weixinProperties.getOffiaccount(appType).getAppId();
        try {
            int contentCheckStatus = Optional.ofNullable(target.getContentCheckStatus())
                    .orElse(ContentCheckStatus.WAITING);

            if (ContentCheckStatus.WAITING != contentCheckStatus) {
                return;
            }

            List<TM> targetMaterialList = targetMaterialRepository.findAllByIdTarget(target);
            boolean hasUncheck = false;
            for (TM targetMaterial : targetMaterialList) {
                int materialCheckStatus = Optional.ofNullable(targetMaterial.getContentCheckStatus())
                        .orElse(ContentCheckStatus.WAITING);
                if (ContentCheckStatus.WAITING == materialCheckStatus) {
                    MaterialInfo materialInfo = targetMaterial.getMaterialInfo();
                    String materialType = materialInfo.getType();
                    String materialInfoType = materialInfo.getInfoType();
                    if ((MaterialInfoType.PATH.equals(materialInfoType)
                            || MaterialInfoType.PRIVATE_PATH.equals(materialInfoType))
                            && (MaterialType.IMAGE.equals(materialType) || MaterialType.AUDIO.equals(materialType))) {

                        String info = materialInfo.getInfo();
                        List<String> urls = JsonUtil.fromJsonArrayString(info, String.class);
                        if (urls.isEmpty() && StringUtils.isNotEmpty(info)) {
                            urls.add(info);
                        }
                        List<String> contentCheckIds = new ArrayList<>();

                        log.debug("checkTargetContent urls: {}", urls);
                        for (String url : urls) {
                            String traceId = weixinService.mediaSecCheck(appType, url, materialType);
                            contentCheckIds.add(traceId);

                            MediaCheckId id = new MediaCheckId(appId, traceId);
                            WxMediaCheckRecord wxMediaCheckRecord = wxMediaCheckRecordRepository.findById(id)
                                    .orElseGet(() -> {
                                        WxMediaCheckRecord newWxMediaCheckRecord = new WxMediaCheckRecord();
                                        newWxMediaCheckRecord.setId(id);
                                        return newWxMediaCheckRecord;
                                    });
                            wxMediaCheckRecord.setUrl(url);
                            wxMediaCheckRecordRepository.save(wxMediaCheckRecord);
                        }

                        if (contentCheckIds.isEmpty()) {
                            hasUncheck = true;
                        } else {
                            targetMaterial.setContentCheckIds(contentCheckIds);
                            targetMaterial.setContentCheckStatus(ContentCheckStatus.CHECKING);
                        }
                    } else {
                        targetMaterial.setContentCheckStatus(ContentCheckStatus.NO_CHECK);
                    }
                }
            }
            targetMaterialRepository.saveAll(targetMaterialList);
            if (!hasUncheck) {
                target.setContentCheckStatus(ContentCheckStatus.CHECKING);
                targetRepository.save(target);
            }
        } catch (Exception e) {
            log.warn("Check {} exception", target.getClass(), e);
        }
    }

    // true 所有内容都安全
    public boolean checkTargetContentResult(T target) {
        int targetContentCheckStatus = target.getContentCheckStatus();
        if (ContentCheckStatus.SAFE_LIST.contains(targetContentCheckStatus)) {
            return true;
        } else if (ContentCheckStatus.CHECKING != target.getContentCheckStatus()) {
            return false;
        }

        List<TM> targetMaterialList = targetMaterialRepository.findAllByIdTarget(target);

        boolean isSafe = true;
        boolean hasUncheck = false;
        for (TM targetMaterial : targetMaterialList) {
            int materialCheckStatus = Optional.ofNullable(targetMaterial.getContentCheckStatus())
                    .orElse(ContentCheckStatus.WAITING);
            if (materialCheckStatus == ContentCheckStatus.WAITING) {
                hasUncheck = true;
            } else if (materialCheckStatus == ContentCheckStatus.CHECKING) {

                List<String> contentCheckIds = targetMaterial.getContentCheckIds();
                if (CollectionUtils.isEmpty(contentCheckIds)) {
                    log.debug("ContentCheckIds is empty.");
                    hasUncheck = true;
                    targetMaterial.setContentCheckStatus(ContentCheckStatus.WAITING);
                    target.setContentCheckStatus(ContentCheckStatus.WAITING);
                } else {
                    Boolean isRisk = this.isContentIdRisk(contentCheckIds);
                    if (isRisk == null) {
                        hasUncheck = true;
                    } else {
                        if (isRisk) {
                            isSafe = false;
                            targetMaterial.setContentCheckStatus(ContentCheckStatus.FAILED);
                        } else {
                            targetMaterial.setContentCheckStatus(ContentCheckStatus.SAFE);
                        }
                    }
                }
            }
        }
        if (!isSafe) {
            target.setContentCheckStatus(ContentCheckStatus.FAILED);
        } else if (!hasUncheck) {
            target.setContentCheckStatus(ContentCheckStatus.SAFE);
        }
        targetMaterialRepository.saveAll(targetMaterialList);
        targetRepository.save(target);
        return isSafe && !hasUncheck;
    }

}
