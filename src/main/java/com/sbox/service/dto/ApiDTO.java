package com.sbox.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Api entity.
 */
public class ApiDTO implements Serializable {

    private Long id;

    private Boolean enable;

    @NotNull
    private String name;

    @NotNull
    private String rechargeUrl;

    private String balanceUrl;

    private String statusUrl;

    private String successCode;

    private String failCode;

    private Double apiCommission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRechargeUrl() {
        return rechargeUrl;
    }

    public void setRechargeUrl(String rechargeUrl) {
        this.rechargeUrl = rechargeUrl;
    }

    public String getBalanceUrl() {
        return balanceUrl;
    }

    public void setBalanceUrl(String balanceUrl) {
        this.balanceUrl = balanceUrl;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public Double getApiCommission() {
        return apiCommission;
    }

    public void setApiCommission(Double apiCommission) {
        this.apiCommission = apiCommission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiDTO apiDTO = (ApiDTO) o;
        if(apiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiDTO{" +
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
