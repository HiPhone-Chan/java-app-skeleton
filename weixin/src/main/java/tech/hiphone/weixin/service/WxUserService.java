package tech.hiphone.weixin.service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.constants.CommonsConstants;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.Authority;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.repository.AuthorityRepository;
import tech.hiphone.commons.repository.UserRepository;
import tech.hiphone.framework.security.RandomUtil;
import tech.hiphone.weixin.domain.WxUser;
import tech.hiphone.weixin.domain.id.WxUserId;
import tech.hiphone.weixin.repository.WxUserRepository;
import tech.hiphone.weixin.security.utils.WxSecurityUtils;

@Service
@Transactional
public class WxUserService {

    private static final Logger log = LoggerFactory.getLogger(WxUserService.class);

    private final UserRepository userRepository;

    private final WxUserRepository wxUserRepository;

    private final AuthorityRepository authorityRepository;

    public WxUserService(UserRepository userRepository, WxUserRepository wxUserRepository,
            AuthorityRepository authorityRepository) {
        super();
        this.userRepository = userRepository;
        this.wxUserRepository = wxUserRepository;
        this.authorityRepository = authorityRepository;
    }

    // 保存微信用户信息
    public WxUser saveWxUser(String appId, Map<String, Object> result) {
        String openId = (String) result.get("openid");
        String unionId = (String) result.get("unionid");
        String nickName = (String) result.get("nickname");
        String headImgUrl = (String) result.get("headimgurl");

        if (StringUtils.isEmpty(openId)) {
            log.warn("Cannot get openId from code");
            throw new BadCredentialsException("Cannot get openId from code");
        }
        WxUser wxUser = this.getWxUser(appId, openId, unionId);
        String sessionKey = (String) result.get("session_key");
        if (StringUtils.isNotEmpty(sessionKey)) {
            wxUser.setSessionKey(sessionKey);
        }
        if (StringUtils.isNotEmpty(headImgUrl)) {
            wxUser.setImageUrl(headImgUrl);
        }
        if (StringUtils.isNotEmpty(nickName)) {
            wxUser.setName(nickName);
        }
        wxUserRepository.save(wxUser);

        return wxUser;
    }

    // 注册一个用新户到微信用户
    public void register(String appId) {
        WxUser wxUser = WxSecurityUtils.getCurrentWxUserId().flatMap(wxUserId -> {
            return wxUserRepository.findById(wxUserId);
        }).orElseThrow();

        User user = wxUser.getUser();
        if (user != null) {
            throw new ServiceException(ErrorCodeContants.USER_EXISTS);
        }

        user = createUser();
        user.setNickName(wxUser.getName());
        user.setImageUrl(wxUser.getImageUrl());
        user.setActivated(true);
        userRepository.save(user);

        wxUser.setUser(user);
        wxUserRepository.save(wxUser);
    }

    private WxUser getWxUser(String appId, String openId, String unionId) {
        WxUserId id = new WxUserId(appId, openId);

        WxUser wxUser = wxUserRepository.findById(id).orElseGet(() -> {
            WxUser newWxUser = new WxUser();
            newWxUser.setId(id);
            return newWxUser;
        });

        if (StringUtils.isEmpty(unionId)) {
            log.debug("Cannot get unionId.");
        } else {
            if (StringUtils.isEmpty(wxUser.getUnionId())) {
                wxUser.setUnionId(unionId);
            }

            if (wxUser.getUser() == null) {
                wxUserRepository.findTopOneByUnionId(unionId).ifPresent(unionWxUser -> {
                    wxUser.setUser(unionWxUser.getUser());
                });
            }
        }

        return wxUser;
    }

    // 微信用户
    private static final String LOGIN_PREFIX = "wx";

    private User createUser() {
        User user = new User();
        String login = (LOGIN_PREFIX + UUID.randomUUID().toString().replaceAll("-", "")).toLowerCase(Locale.ENGLISH);
        user.setLogin(login);
        user.setLangKey(CommonsConstants.DEFAULT_LANGUAGE);
        String encryptedPassword = RandomUtil.generatePassword();
        user.setPassword(encryptedPassword);
        user.setActivated(true);

        Set<Authority> authorities = Arrays.asList(AuthoritiesConstants.USER).stream()
                .map(authorityRepository::findById).filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toSet());
        user.setAuthorities(authorities);
        userRepository.save(user);
        return user;
    }

}
