package com.sbox.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Operator entity.
 */
public class OperatorDTO implements Serializable {

    private Long id;

    private Boolean enable;

    @NotNull
    private String name;

    @NotNull
    private String normalCode;

    private String specialCode;

    private Long firstApiId;

    private String firstApiName;

    private Long secondApiId;

    private String secondApiName;

    private Long thirdApiId;

    private String thirdApiName;

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

    public String getNormalCode() {
        return normalCode;
    }

    public void setNormalCode(String normalCode) {
        this.normalCode = normalCode;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public Long getFirstApiId() {
        return firstApiId;
    }

    public void setFirstApiId(Long apiId) {
        this.firstApiId = apiId;
    }

    public String getFirstApiName() {
        return firstApiName;
    }

    public void setFirstApiName(String apiName) {
        this.firstApiName = apiName;
    }

    public Long getSecondApiId() {
        return secondApiId;
    }

    public void setSecondApiId(Long apiId) {
        this.secondApiId = apiId;
    }

    public String getSecondApiName() {
        return secondApiName;
    }

    public void setSecondApiName(String apiName) {
        this.secondApiName = apiName;
    }

    public Long getThirdApiId() {
        return thirdApiId;
    }

    public void setThirdApiId(Long apiId) {
        this.thirdApiId = apiId;
    }

    public String getThirdApiName() {
        return thirdApiName;
    }

    public void setThirdApiName(String apiName) {
        this.thirdApiName = apiName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperatorDTO operatorDTO = (OperatorDTO) o;
        if(operatorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operatorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperatorDTO{" +
            "id=" + getId() +
            ", enable='" + isEnable() + "'" +
            ", name='" + getName() + "'" +
            ", normalCode='" + getNormalCode() + "'" +
            ", specialCode='" + getSpecialCode() + "'" +
            "}";
    }
}
