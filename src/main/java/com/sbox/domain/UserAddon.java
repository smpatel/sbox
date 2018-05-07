package com.sbox.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserAddon.
 */
@Entity
@Table(name = "user_addon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAddon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(max = 60)
    @Column(name = "jhi_password", length = 60, nullable = false)
    private String password;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "activated")
    private Boolean activated;

    @JsonIgnore
    @NotNull
    @Column(name = "parent_user_id", nullable = false)
    private Long parentUserId;

    @JsonIgnore
    @Column(name = "security_password")
    private String securityPassword;

    @NotNull
    @Column(name = "authorized_mobile", nullable = false)
    private Long authorizedMobile;

    @Column(name = "secondary_mobile")
    private Long secondaryMobile;

    @Column(name = "acc_open_fee")
    private Integer accOpenFee;

    @Column(name = "daily_rental")
    private Integer dailyRental;

    @Column(name = "minimum_balance")
    private Integer minimumBalance;

    @Column(name = "flat_commission")
    private Double flatCommission;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "api_response_url")
    private String apiResponseUrl;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private CommissionGroup commissionGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserAddon name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public UserAddon username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public UserAddon password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public UserAddon email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivated() {
        return activated;
    }

    public UserAddon activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Long getParentUserId() {
        return parentUserId;
    }

    public UserAddon parentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
        return this;
    }

    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public UserAddon securityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
        return this;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public Long getAuthorizedMobile() {
        return authorizedMobile;
    }

    public UserAddon authorizedMobile(Long authorizedMobile) {
        this.authorizedMobile = authorizedMobile;
        return this;
    }

    public void setAuthorizedMobile(Long authorizedMobile) {
        this.authorizedMobile = authorizedMobile;
    }

    public Long getSecondaryMobile() {
        return secondaryMobile;
    }

    public UserAddon secondaryMobile(Long secondaryMobile) {
        this.secondaryMobile = secondaryMobile;
        return this;
    }

    public void setSecondaryMobile(Long secondaryMobile) {
        this.secondaryMobile = secondaryMobile;
    }

    public Integer getAccOpenFee() {
        return accOpenFee;
    }

    public UserAddon accOpenFee(Integer accOpenFee) {
        this.accOpenFee = accOpenFee;
        return this;
    }

    public void setAccOpenFee(Integer accOpenFee) {
        this.accOpenFee = accOpenFee;
    }

    public Integer getDailyRental() {
        return dailyRental;
    }

    public UserAddon dailyRental(Integer dailyRental) {
        this.dailyRental = dailyRental;
        return this;
    }

    public void setDailyRental(Integer dailyRental) {
        this.dailyRental = dailyRental;
    }

    public Integer getMinimumBalance() {
        return minimumBalance;
    }

    public UserAddon minimumBalance(Integer minimumBalance) {
        this.minimumBalance = minimumBalance;
        return this;
    }

    public void setMinimumBalance(Integer minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Double getFlatCommission() {
        return flatCommission;
    }

    public UserAddon flatCommission(Double flatCommission) {
        this.flatCommission = flatCommission;
        return this;
    }

    public void setFlatCommission(Double flatCommission) {
        this.flatCommission = flatCommission;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public UserAddon apiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiResponseUrl() {
        return apiResponseUrl;
    }

    public UserAddon apiResponseUrl(String apiResponseUrl) {
        this.apiResponseUrl = apiResponseUrl;
        return this;
    }

    public void setApiResponseUrl(String apiResponseUrl) {
        this.apiResponseUrl = apiResponseUrl;
    }

    public User getUser() {
        return user;
    }

    public UserAddon user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommissionGroup getCommissionGroup() {
        return commissionGroup;
    }

    public UserAddon commissionGroup(CommissionGroup commissionGroup) {
        this.commissionGroup = commissionGroup;
        return this;
    }

    public void setCommissionGroup(CommissionGroup commissionGroup) {
        this.commissionGroup = commissionGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAddon userAddon = (UserAddon) o;
        if (userAddon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAddon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAddon{" +
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
            ", user='" + getUser().toString() + "'" +
            "}";
    }
}
