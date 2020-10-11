package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.StatusEnum;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Lob
    @Column(name = "exception")
    private byte[] exception;

    @Column(name = "exception_content_type")
    private String exceptionContentType;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "total_to_create")
    private Integer totalToCreate;

    @Column(name = "total_created")
    private Integer totalCreated;

    @OneToMany(mappedBy = "status")
    private Set<SignatureLevel> signatureLevels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Status status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public byte[] getException() {
        return exception;
    }

    public Status exception(byte[] exception) {
        this.exception = exception;
        return this;
    }

    public void setException(byte[] exception) {
        this.exception = exception;
    }

    public String getExceptionContentType() {
        return exceptionContentType;
    }

    public Status exceptionContentType(String exceptionContentType) {
        this.exceptionContentType = exceptionContentType;
        return this;
    }

    public void setExceptionContentType(String exceptionContentType) {
        this.exceptionContentType = exceptionContentType;
    }

    public Integer getMonth() {
        return month;
    }

    public Status month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public Status year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTotalToCreate() {
        return totalToCreate;
    }

    public Status totalToCreate(Integer totalToCreate) {
        this.totalToCreate = totalToCreate;
        return this;
    }

    public void setTotalToCreate(Integer totalToCreate) {
        this.totalToCreate = totalToCreate;
    }

    public Integer getTotalCreated() {
        return totalCreated;
    }

    public Status totalCreated(Integer totalCreated) {
        this.totalCreated = totalCreated;
        return this;
    }

    public void setTotalCreated(Integer totalCreated) {
        this.totalCreated = totalCreated;
    }

    public Set<SignatureLevel> getSignatureLevels() {
        return signatureLevels;
    }

    public Status signatureLevels(Set<SignatureLevel> signatureLevels) {
        this.signatureLevels = signatureLevels;
        return this;
    }

    public Status addSignatureLevels(SignatureLevel signatureLevel) {
        this.signatureLevels.add(signatureLevel);
        signatureLevel.setStatus(this);
        return this;
    }

    public Status removeSignatureLevels(SignatureLevel signatureLevel) {
        this.signatureLevels.remove(signatureLevel);
        signatureLevel.setStatus(null);
        return this;
    }

    public void setSignatureLevels(Set<SignatureLevel> signatureLevels) {
        this.signatureLevels = signatureLevels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", exception='" + getException() + "'" +
            ", exceptionContentType='" + getExceptionContentType() + "'" +
            ", month=" + getMonth() +
            ", year=" + getYear() +
            ", totalToCreate=" + getTotalToCreate() +
            ", totalCreated=" + getTotalCreated() +
            "}";
    }
}
