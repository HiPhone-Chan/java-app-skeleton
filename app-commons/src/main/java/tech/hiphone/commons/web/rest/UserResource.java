package tech.hiphone.commons.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.constants.CommonsConstants;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.Authority;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.repository.UserRepository;
import tech.hiphone.commons.service.UserService;
import tech.hiphone.commons.service.dto.AdminUserDTO;
import tech.hiphone.commons.service.dto.PasswordChangeDTO;
import tech.hiphone.framework.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/admin")
public class UserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections
            .unmodifiableList(Arrays.asList("id", "login", "nickName", "mobile", "email", "activated", "langKey",
                    "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"));
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody AdminUserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new ServiceException(ErrorCodeContants.BAD_PARAMETERS, "A new user cannot already have an ID");
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new ServiceException(ErrorCodeContants.LOGIN_ALREADY_USED);
        }
        User newUser = userService.createUser(userDTO);
        return newUser;
    }

    @PutMapping("/user")
    public ResponseEntity<AdminUserDTO> updateUser(@Valid @RequestBody AdminUserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new ServiceException(ErrorCodeContants.LOGIN_ALREADY_USED);
        }
        Optional<AdminUserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser);
    }

    @PostMapping("/user/change-password/{login:" + CommonsConstants.LOGIN_REGEX + "}")
    public void changePassword(@PathVariable String login, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        if (AccountResource.isPasswordLengthInvalid(passwordChangeDTO.getNewPassword())) {
            throw new ServiceException(ErrorCodeContants.INVALID_PASSWORD, "Password is invalid.");
        }
        User subordinate = userRepository.findOneByLogin(login).orElseThrow();
        userService.changePasswordBySuperior(subordinate, passwordChangeDTO.getCurrentPassword(),
                passwordChangeDTO.getNewPassword());
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDTO>> getUsers(Pageable pageable,
            @RequestParam(name = "authority", required = false) String authority,
            @RequestParam(name = "search", required = false) String search) {
        log.debug("REST request to get all User for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> andList = new ArrayList<>();

            if (StringUtils.isNotEmpty(authority)) {
                SetJoin<User, Authority> setJoin = root.joinSet("authorities", JoinType.LEFT);
                List<String> authorities = Arrays.asList(authority);
                andList.add(setJoin.get("name").in(authorities));
            }

            if (StringUtils.isNotEmpty(search)) {
                List<Predicate> orList = new ArrayList<>();
                String like = "%" + search + "%";

                orList.add(criteriaBuilder.like(root.get("login"), like));
                orList.add(criteriaBuilder.like(root.get("nickName"), like));
                orList.add(criteriaBuilder.like(root.get("mobile"), like));

                andList.add(criteriaBuilder.or(orList.toArray(new Predicate[orList.size()])));
            }

            query.where(criteriaBuilder.and(andList.toArray(new Predicate[andList.size()])));
            return query.getRestriction();
        };
        final Page<AdminUserDTO> page = userService.getAllManagedUsers(spec, pageable);
        return ResponseUtil.wrapPage(page);
    }

    @GetMapping("/user/{login:" + CommonsConstants.LOGIN_REGEX + "}")
    public ResponseEntity<AdminUserDTO> getUser(@PathVariable @Pattern(regexp = CommonsConstants.LOGIN_REGEX) String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(userService.getUserWithAuthoritiesByLogin(login).map(AdminUserDTO::new));
    }

    @DeleteMapping("/user/{login:" + CommonsConstants.LOGIN_REGEX + "}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
    }

    @GetMapping("/user/check/{login:" + CommonsConstants.LOGIN_REGEX + "}")
    public boolean checkLogin(@PathVariable String login) {
        return userRepository.existsByLogin(login);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

}
