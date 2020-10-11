package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SignatureLevel.
 */
@Entity
@Table(name = "signature_level")
public class SignatureLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "level_1")
    private Integer level1;

    @Column(name = "level_2")
    private Integer level2;

    @Column(name = "level_3")
    private Integer level3;

    @ManyToOne
    @JsonIgnoreProperties(value = "signatureLevels", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public SignatureLevel orgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public SignatureLevel orgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getLevel1() {
        return level1;
    }

    public SignatureLevel level1(Integer level1) {
        this.level1 = level1;
        return this;
    }

    public void setLevel1(Integer level1) {
        this.level1 = level1;
    }

    public Integer getLevel2() {
        return level2;
    }

    public SignatureLevel level2(Integer level2) {
        this.level2 = level2;
        return this;
    }

    public void setLevel2(Integer level2) {
        this.level2 = level2;
    }

    public Integer getLevel3() {
        return level3;
    }

    public SignatureLevel level3(Integer level3) {
        this.level3 = level3;
        return this;
    }

    public void setLevel3(Integer level3) {
        this.level3 = level3;
    }

    public Status getStatus() {
        return status;
    }

    public SignatureLevel status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignatureLevel)) {
            return false;
        }
        return id != null && id.equals(((SignatureLevel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignatureLevel{" +
            "id=" + getId() +
            ", orgName='" + getOrgName() + "'" +
            ", orgId='" + getOrgId() + "'" +
            ", level1=" + getLevel1() +
            ", level2=" + getLevel2() +
            ", level3=" + getLevel3() +
            "}";
    }
}
