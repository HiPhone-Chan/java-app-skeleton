package tech.hiphone.commons.web.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.repository.UserRepository;
import tech.hiphone.commons.security.SecurityUtils;
import tech.hiphone.commons.service.UserService;
import tech.hiphone.commons.service.dto.AdminUserDTO;
import tech.hiphone.commons.service.dto.PasswordChangeDTO;
import tech.hiphone.commons.web.vm.ManagedUserVM;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    public AccountResource(UserRepository userRepository, UserService userService) {
        super();
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @GetMapping("/account")
    public AdminUserDTO getAccount() {
        return userService.getUserWithAuthorities().map(AdminUserDTO::new).orElseThrow();
    }

    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody AdminUserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow();
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Current user login not found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getNickName(),
                userDTO.getMobile(), userDTO.getImageUrl());
    }

    @PostMapping("/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        if (isPasswordLengthInvalid(passwordChangeDTO.getNewPassword())) {
            throw new ServiceException(ErrorCodeContants.INVALID_PASSWORD, "Password is invalid.");
        }
        userService.changePassword(passwordChangeDTO.getCurrentPassword(), passwordChangeDTO.getNewPassword());
    }

    public static boolean isPasswordLengthInvalid(String password) {
        return (StringUtils.isEmpty(password) || password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH
                || password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH);
    }

}
