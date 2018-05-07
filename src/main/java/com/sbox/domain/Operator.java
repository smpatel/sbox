package com.sbox.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Operator.
 */
@Entity
@Table(name = "operator")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operator implements Serializable {

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
    @Column(name = "normal_code", nullable = false)
    private String normalCode;

    @Column(name = "special_code")
    private String specialCode;

    @ManyToOne
    private Api firstApi;

    @ManyToOne
    private Api secondApi;

    @ManyToOne
    private Api thirdApi;

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

    public Operator enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public Operator name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalCode() {
        return normalCode;
    }

    public Operator normalCode(String normalCode) {
        this.normalCode = normalCode;
        return this;
    }

    public void setNormalCode(String normalCode) {
        this.normalCode = normalCode;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public Operator specialCode(String specialCode) {
        this.specialCode = specialCode;
        return this;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public Api getFirstApi() {
        return firstApi;
    }

    public Operator firstApi(Api api) {
        this.firstApi = api;
        return this;
    }

    public void setFirstApi(Api api) {
        this.firstApi = api;
    }

    public Api getSecondApi() {
        return secondApi;
    }

    public Operator secondApi(Api api) {
        this.secondApi = api;
        return this;
    }

    public void setSecondApi(Api api) {
        this.secondApi = api;
    }

    public Api getThirdApi() {
        return thirdApi;
    }

    public Operator thirdApi(Api api) {
        this.thirdApi = api;
        return this;
    }

    public void setThirdApi(Api api) {
        this.thirdApi = api;
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
        Operator operator = (Operator) o;
        if (operator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operator{" +
            "id=" + getId() +
            ", enable='" + isEnable() + "'" +
            ", name='" + getName() + "'" +
            ", normalCode='" + getNormalCode() + "'" +
            ", specialCode='" + getSpecialCode() + "'" +
            "}";
    }
}
