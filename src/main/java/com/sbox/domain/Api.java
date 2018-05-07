package com.sbox.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Api.
 */
@Entity
@Table(name = "api")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Api implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_enable")
    private Boolean enable;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "recharge_url", nullable = false)
    private String rechargeUrl;

    @Column(name = "balance_url")
    private String balanceUrl;

    @Column(name = "status_url")
    private String statusUrl;

    @Column(name = "success_code")
    private String successCode;

    @Column(name = "fail_code")
    private String failCode;

    @Column(name = "api_commission")
    private Double apiCommission;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEnable() {
        return enable;
    }

    public Api enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public Api name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRechargeUrl() {
        return rechargeUrl;
    }

    public Api rechargeUrl(String rechargeUrl) {
        this.rechargeUrl = rechargeUrl;
        return this;
    }

    public void setRechargeUrl(String rechargeUrl) {
        this.rechargeUrl = rechargeUrl;
    }

    public String getBalanceUrl() {
        return balanceUrl;
    }

    public Api balanceUrl(String balanceUrl) {
        this.balanceUrl = balanceUrl;
        return this;
    }

    public void setBalanceUrl(String balanceUrl) {
        this.balanceUrl = balanceUrl;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public Api statusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
        return this;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public Api successCode(String successCode) {
        this.successCode = successCode;
        return this;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getFailCode() {
        return failCode;
    }

    public Api failCode(String failCode) {
        this.failCode = failCode;
        return this;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public Double getApiCommission() {
        return apiCommission;
    }

    public Api apiCommission(Double apiCommission) {
        this.apiCommission = apiCommission;
        return this;
    }

    public void setApiCommission(Double apiCommission) {
        this.apiCommission = apiCommission;
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
        Api api = (Api) o;
        if (api.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), api.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Api{" +
            "id=" + getId() +
            ", enable='" + isEnable() + "'" +
            ", name='" + getName() + "'" +
            ", rechargeUrl='" + getRechargeUrl() + "'" +
            ", balanceUrl='" + getBalanceUrl() + "'" +
            ", statusUrl='" + getStatusUrl() + "'" +
            ", successCode='" + getSuccessCode() + "'" +
            ", failCode='" + getFailCode() + "'" +
            ", apiCommission=" + getApiCommission() +
            "}";
    }
}
