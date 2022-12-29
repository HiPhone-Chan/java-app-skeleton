package tech.hiphone.weixin.web.rest;

import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.security.jwt.JWTFilter;
import tech.hiphone.commons.security.jwt.JWTToken;
import tech.hiphone.commons.security.jwt.TokenProvider;
import tech.hiphone.commons.web.vm.LoginVM;
import tech.hiphone.weixin.constants.WxAuthoritiesConstants;
import tech.hiphone.weixin.domain.id.WxUserId;
import tech.hiphone.weixin.security.authentication.SwitchWeixinAuthenticationToken;
import tech.hiphone.weixin.security.authentication.WeixinAuthenticationToken;
import tech.hiphone.weixin.security.utils.WxSecurityUtils;
import tech.hiphone.weixin.service.WxUserService;
import tech.hiphone.weixin.web.vm.AuthorizationCodeVM;
import tech.hiphone.weixin.web.vm.RegisterVM;

@SuppressWarnings("unchecked")
@RestController
public class WxUserResource {

    public final static int DEFAULT_CODE_EXPIRE_TIME = 5 * 60 * 1000; // mill

    private static final Logger log = LoggerFactory.getLogger(WxUserResource.class);

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final WxUserService wxUserService;

    public WxUserResource(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider,
            WxUserService wxUserService) {
        super();
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.wxUserService = wxUserService;
    }

    // 通过code换取token
    @PostMapping(path = "/api/weixin/authenticate")
    @PermitAll()
    public ResponseEntity<?> authorize(@RequestBody AuthorizationCodeVM code, HttpServletRequest request) {
        log.debug("Request to weixin authorize");
        String appId = code.getAppId();
        WeixinAuthenticationToken authenticationToken = new WeixinAuthenticationToken(appId, code.getCode());
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, DEFAULT_CODE_EXPIRE_TIME);

        Map<String, Object> result = (Map<String, Object>) authentication.getDetails();
        wxUserService.saveWxUser(appId, result);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping(path = "/api/weixin/register")
    @Secured({ WxAuthoritiesConstants.WEIXIN })
    public void register(@Valid @RequestBody RegisterVM registerVM) {
        wxUserService.register(registerVM.getAppId());
    }

    @PostMapping(path = "/api/weixin/login")
    @Secured({ WxAuthoritiesConstants.WEIXIN })
    public ResponseEntity<?> login(@RequestBody LoginVM loginVM, HttpServletRequest request) {
        WxUserId wxUserId = WxSecurityUtils.getCurrentWxUserId().orElseThrow();
        SwitchWeixinAuthenticationToken authenticationToken = new SwitchWeixinAuthenticationToken(wxUserId, null);
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, JWTFilter.BEARER_PREFIX + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

}
