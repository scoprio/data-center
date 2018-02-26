package com.ulb.data.center.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DcAuthority.
 */
@Entity
@Table(name = "dc_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DcAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;

    @Column(name = "level")
    private Integer level;

    @ManyToOne
    private DcDepartment department;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dc_authority_dc_region",
               joinColumns = @JoinColumn(name="dc_authorities_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="dc_regions_id", referencedColumnName="id"))
    private Set<DcRegion> dcRegions = new HashSet<>();

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

    public DcAuthority name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartDate() {
        return startDate;
    }

    public DcAuthority startDate(Long startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public DcAuthority endDate(Long endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getLevel() {
        return level;
    }

    public DcAuthority level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public DcDepartment getDepartment() {
        return department;
    }

    public DcAuthority departmentId(DcDepartment dcDepartment) {
        this.department = dcDepartment;
        return this;
    }

    public void setDepartmentId(DcDepartment dcDepartment) {
        this.department = dcDepartment;
    }

    public Set<DcRegion> getDcRegions() {
        return dcRegions;
    }

    public DcAuthority dcRegions(Set<DcRegion> dcRegions) {
        this.dcRegions = dcRegions;
        return this;
    }

    public DcAuthority addDcRegion(DcRegion dcRegion) {
        this.dcRegions.add(dcRegion);
        dcRegion.getDcAuthorities().add(this);
        return this;
    }

    public DcAuthority removeDcRegion(DcRegion dcRegion) {
        this.dcRegions.remove(dcRegion);
        dcRegion.getDcAuthorities().remove(this);
        return this;
    }

    public void setDcRegions(Set<DcRegion> dcRegions) {
        this.dcRegions = dcRegions;
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
        DcAuthority dcAuthority = (DcAuthority) o;
        if (dcAuthority.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dcAuthority.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DcAuthority{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate=" + getStartDate() +
            ", endDate=" + getEndDate() +
            ", level=" + getLevel() +
            "}";
    }
}
