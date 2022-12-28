package tech.hiphone.cms.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tech.hiphone.cms.config.CmsProperties;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.utils.SslUtil;
import tech.hiphone.framework.config.ProfileConstants;
import tech.hiphone.framework.utils.FileUtil;

@Service
public class PathService {

    private static final Logger log = LoggerFactory.getLogger(PathService.class);

    private Environment env;

    private CmsProperties configProperties;

    private RestTemplate restTemplate;

    public PathService(Environment env, CmsProperties configProperties) {
        super();
        this.env = env;
        this.configProperties = configProperties;
        this.restTemplate = new RestTemplate(SslUtil.sslRequestFactory());
    }

    public String getLocalPath() {
        String localPath = configProperties.getBasePath().getLocal();
        if (env.acceptsProfiles(Profiles.of(ProfileConstants.SPRING_PROFILE_DEVELOPMENT))) {
            return System.getProperty("user.home") + localPath;
        }
        return localPath;
    }

    public String getWebPath() {
        String webPath = configProperties.getBasePath().getWeb();
        if (StringUtils.isEmpty(webPath)) {
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find webPath.");
        }
        return webPath;
    }

    // 通过路径获取文件
    public File getFileFromWebPath(String path) {
        // 本地图片
        if (path.startsWith(this.getWebPath())) {
            String filePath = this.getLocalPath() + path.replace(this.getWebPath() + "/resource/material", "");
            log.debug("get filepath {}", filePath);
            return new File(filePath);
        }

        try {
            byte[] imageData = restTemplate.getForObject(path, byte[].class);
            File tmpFile = File.createTempFile("cms/", ".tmp");
            FileUtil.writeFile(imageData, tmpFile);
            return tmpFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find file.");
    }

}
