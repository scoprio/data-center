package com.ulb.data.center.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DcRegion.
 */
@Entity
@Table(name = "dc_region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DcRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "adcode")
    private String adcode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "level")
    private Integer level;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToMany(mappedBy = "dcRegions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DcAuthority> dcAuthorities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public DcRegion code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegionName() {
        return regionName;
    }

    public DcRegion regionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public DcRegion regionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getProvince() {
        return province;
    }

    public DcRegion province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public DcRegion city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public DcRegion district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdcode() {
        return adcode;
    }

    public DcRegion adcode(String adcode) {
        this.adcode = adcode;
        return this;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public DcRegion zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getLevel() {
        return level;
    }

    public DcRegion level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public DcRegion parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<DcAuthority> getDcAuthorities() {
        return dcAuthorities;
    }

    public DcRegion dcAuthorities(Set<DcAuthority> dcAuthorities) {
        this.dcAuthorities = dcAuthorities;
        return this;
    }

    public DcRegion addDcAuthority(DcAuthority DcAuthority) {
        this.dcAuthorities.add(DcAuthority);
        DcAuthority.getDcRegions().add(this);
        return this;
    }

    public DcRegion removeDcAuthority(DcAuthority DcAuthority) {
        this.dcAuthorities.remove(DcAuthority);
        DcAuthority.getDcRegions().remove(this);
        return this;
    }

    public void setDcAuthorities(Set<DcAuthority> dcAuthorities) {
        this.dcAuthorities = dcAuthorities;
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
        DcRegion dcRegion = (DcRegion) o;
        if (dcRegion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dcRegion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DcRegion{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", regionName='" + getRegionName() + "'" +
            ", regionCode='" + getRegionCode() + "'" +
            ", province='" + getProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", district='" + getDistrict() + "'" +
            ", adcode='" + getAdcode() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", level=" + getLevel() +
            ", parentId=" + getParentId() +
            "}";
    }
}
