package tech.hiphone.weixin.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import tech.hiphone.framework.jpa.support.JpaExtRepositoryFactoryBean;
import tech.hiphone.weixin.config.WeixinProperties.WeixinBaseInfo;
import tech.hiphone.weixin.config.WeixinProperties.WeixinMiniProgram;
import tech.hiphone.weixin.config.WeixinProperties.WeixinOffiaccount;
import tech.hiphone.weixin.config.WeixinProperties.WeixinPayBaseInfo;
import tech.hiphone.weixin.constants.WeixinConstants;
import tech.hiphone.weixin.domain.WxMiniProgram;
import tech.hiphone.weixin.domain.WxOffiaccount;
import tech.hiphone.weixin.domain.WxOplatform;
import tech.hiphone.weixin.domain.WxPay;
import tech.hiphone.weixin.repository.WxMiniProgramRepository;
import tech.hiphone.weixin.repository.WxOffiaccountRepository;
import tech.hiphone.weixin.repository.WxOplatformRepository;
import tech.hiphone.weixin.repository.WxPayRepository;

@Configuration
//@EnableConfigurationProperties({ WeixinProperties.class })
@ComponentScan(WeixinConstants.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = WeixinConstants.BASE_PACKAGE
        + ".repository", repositoryFactoryBeanClass = JpaExtRepositoryFactoryBean.class)
@EntityScan({ WeixinConstants.BASE_PACKAGE })
public class WeixinConfiguration {

    private final WxOffiaccountRepository wxOffiaccountRepository;

    private final WxMiniProgramRepository wxMiniProgramRepository;

    private final WxPayRepository wxPayRepository;

    private final WxOplatformRepository wxOplatformRepository;

    public WeixinConfiguration(WxOffiaccountRepository wxOffiaccountRepository,
            WxMiniProgramRepository wxMiniProgramRepository, WxPayRepository wxPayRepository,
            WxOplatformRepository wxOplatformRepository) {
        super();
        this.wxOffiaccountRepository = wxOffiaccountRepository;
        this.wxMiniProgramRepository = wxMiniProgramRepository;
        this.wxPayRepository = wxPayRepository;
        this.wxOplatformRepository = wxOplatformRepository;
    }

    @Bean
    public WeixinProperties weixinProperties() {
        WeixinProperties weixinProperties = new WeixinProperties();
        initWxOffiaccount(weixinProperties);
        initWxMiniProgram(weixinProperties);
        initWxPay(weixinProperties);
        initWxOplatform(weixinProperties);
        return weixinProperties;

    }

    private void initWxOffiaccount(WeixinProperties weixinProperties) {
        List<WxOffiaccount> list = wxOffiaccountRepository.findAll();
        Map<String, WeixinOffiaccount> map = weixinProperties.getOffiaccount();
        for (WxOffiaccount item : list) {
            WeixinOffiaccount weixinOffiaccount = weixinProperties.new WeixinOffiaccount(item);
            map.put(item.getAppId(), weixinOffiaccount);
        }
    }

    private void initWxMiniProgram(WeixinProperties weixinProperties) {
        List<WxMiniProgram> list = wxMiniProgramRepository.findAll();
        Map<String, WeixinMiniProgram> map = weixinProperties.getMiniprogram();
        Map<String, WeixinOffiaccount> offiaccountMap = weixinProperties.getOffiaccount();

        for (WxMiniProgram item : list) {
            WeixinMiniProgram weixinMiniProgram = weixinProperties.new WeixinMiniProgram(item);
            String appId = item.getAppId();
            map.put(appId, weixinMiniProgram);
            offiaccountMap.remove(appId);
        }
    }

    private void initWxPay(WeixinProperties weixinProperties) {
        List<WxPay> list = wxPayRepository.findAll();
        Map<String, WeixinPayBaseInfo> map = weixinProperties.getWxpay();
        for (WxPay item : list) {
            WeixinPayBaseInfo weixinPayBaseInfo = weixinProperties.new WeixinPayBaseInfo(item);
            map.put(item.getOrderType(), weixinPayBaseInfo);
        }
    }

    private void initWxOplatform(WeixinProperties weixinProperties) {
        List<WxOplatform> list = wxOplatformRepository.findAll();
        Map<String, WeixinBaseInfo> map = weixinProperties.getOplatform();
        for (WxOplatform item : list) {
            WeixinBaseInfo weixinBaseInfo = new WeixinBaseInfo(item);
            weixinBaseInfo.setType(WeixinConstants.TYPE_OPLATFORM);
            map.put(item.getAppId(), weixinBaseInfo);
        }
    }

}
