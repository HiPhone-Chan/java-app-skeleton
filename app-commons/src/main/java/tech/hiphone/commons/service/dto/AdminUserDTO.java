package tech.hiphone.commons.service.dto;

import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import tech.hiphone.commons.domain.Authority;
import tech.hiphone.commons.domain.User;

/**
 * A DTO representing a user, with his authorities.
 */
public class AdminUserDTO extends AbstractUserDetailDTO {

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 20)
    private String mobile; // 电话

    @Email
    @Size(min = 5, max = 254)
    private String email;

    public AdminUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public AdminUserDTO(User user) {
        super(user);
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.mobile = user.getMobile();
        this.email = user.getEmail();
        this.setAuthorities(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("AdminUserDTO [firstName=%s, lastName=%s, mobile=%s, email=%s]", firstName, lastName,
                mobile, email);
    }
}
