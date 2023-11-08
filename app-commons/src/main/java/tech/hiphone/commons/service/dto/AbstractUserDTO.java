package tech.hiphone.commons.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import tech.hiphone.commons.constants.CommonsConstants;
import tech.hiphone.commons.domain.common.AbstractUser;

/**
 * A DTO representing a user, with his authorities.
 */
public abstract class AbstractUserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = CommonsConstants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String nickName;;

    @Size(min = 1, max = 255)
    private String principal;

    @Size(max = 256)
    private String imageUrl;

    @Size(min = 2, max = 10)
    private String langKey;

    public AbstractUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public AbstractUserDTO(AbstractUser user) {
        this.nickName = user.getNickName();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.principal = user.getPrincipal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    @Override
    public String toString() {
        return String.format("CustomerUserDTO [id=%s, login=%s, nickName=%s, principal=%s, imageUrl=%s, langKey=%s]",
                id, login, nickName, principal, imageUrl, langKey);
    }
}
