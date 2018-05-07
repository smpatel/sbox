package com.sbox.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserAddon entity.
 */
public class UserAddonDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 50)
    private String username;

    @NotNull
    @Size(max = 60)
    private String password;

    @NotNull
    private String email;

    private Boolean activated;

    @NotNull
    private Long parentUserId;

    private String securityPassword;

    @NotNull
    private Long authorizedMobile;

    private Long secondaryMobile;

    private Integer accOpenFee;

    private Integer dailyRental;

    private Integer minimumBalance;

    private Double flatCommission;

    private String apiUrl;

    private String apiResponseUrl;

    private Long userId;

    private String userLogin;

    private Long commissionGroupId;

    private String commissionGroupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public Long getAuthorizedMobile() {
        return authorizedMobile;
    }

    public void setAuthorizedMobile(Long authorizedMobile) {
        this.authorizedMobile = authorizedMobile;
    }

    public Long getSecondaryMobile() {
        return secondaryMobile;
    }

    public void setSecondaryMobile(Long secondaryMobile) {
        this.secondaryMobile = secondaryMobile;
    }

    public Integer getAccOpenFee() {
        return accOpenFee;
    }

    public void setAccOpenFee(Integer accOpenFee) {
        this.accOpenFee = accOpenFee;
    }

    public Integer getDailyRental() {
        return dailyRental;
    }

    public void setDailyRental(Integer dailyRental) {
        this.dailyRental = dailyRental;
    }

    public Integer getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Integer minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Double getFlatCommission() {
        return flatCommission;
    }

    public void setFlatCommission(Double flatCommission) {
        this.flatCommission = flatCommission;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiResponseUrl() {
        return apiResponseUrl;
    }

    public void setApiResponseUrl(String apiResponseUrl) {
        this.apiResponseUrl = apiResponseUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getCommissionGroupId() {
        return commissionGroupId;
    }

    public void setCommissionGroupId(Long commissionGroupId) {
        this.commissionGroupId = commissionGroupId;
    }

    public String getCommissionGroupName() {
        return commissionGroupName;
    }

    public void setCommissionGroupName(String commissionGroupName) {
        this.commissionGroupName = commissionGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAddonDTO userAddonDTO = (UserAddonDTO) o;
        if(userAddonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAddonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAddonDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", activated='" + isActivated() + "'" +
            ", parentUserId=" + getParentUserId() +
            ", securityPassword='" + getSecurityPassword() + "'" +
            ", authorizedMobile=" + getAuthorizedMobile() +
            ", secondaryMobile=" + getSecondaryMobile() +
            ", accOpenFee=" + getAccOpenFee() +
            ", dailyRental=" + getDailyRental() +
            ", minimumBalance=" + getMinimumBalance() +
            ", flatCommission=" + getFlatCommission() +
            ", apiUrl='" + getApiUrl() + "'" +
            ", apiResponseUrl='" + getApiResponseUrl() + "'" +
            "}";
    }
}
