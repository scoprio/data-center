package com.ulb.data.center.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DcDepartment.
 */
@Entity
@Table(name = "dc_department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DcDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    @Column(name = "is_enable")
    private Boolean isEnable;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dc_department_dc_menu",
               joinColumns = @JoinColumn(name="dc_departments_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="dc_menus_id", referencedColumnName="id"))
    private Set<DcMenu> dcMenus = new HashSet<>();

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

    public DcDepartment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DcDepartment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public DcDepartment createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public DcDepartment updateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean isEnable() {
        return isEnable;
    }

    public DcDepartment isEnable(Boolean isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Set<DcMenu> getDcMenus() {
        return dcMenus;
    }

    public DcDepartment dcMenus(Set<DcMenu> dcMenus) {
        this.dcMenus = dcMenus;
        return this;
    }

    public DcDepartment addDcMenu(DcMenu dcMenu) {
        this.dcMenus.add(dcMenu);
        dcMenu.getDcDepartments().add(this);
        return this;
    }

    public DcDepartment removeDcMenu(DcMenu dcMenu) {
        this.dcMenus.remove(dcMenu);
        dcMenu.getDcDepartments().remove(this);
        return this;
    }

    public void setDcMenus(Set<DcMenu> dcMenus) {
        this.dcMenus = dcMenus;
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
        DcDepartment dcDepartment = (DcDepartment) o;
        if (dcDepartment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dcDepartment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DcDepartment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createTime=" + getCreateTime() +
            ", updateTime=" + getUpdateTime() +
            ", isEnable='" + isEnable() + "'" +
            "}";
    }
}
