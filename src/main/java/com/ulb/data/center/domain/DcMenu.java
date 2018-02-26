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
 * A DcMenu.
 */
@Entity
@Table(name = "dc_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DcMenu implements Serializable {

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

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "url")
    private String url;

    @ManyToMany(mappedBy = "dcMenus")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DcDepartment> dcDepartments = new HashSet<>();

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

    public DcMenu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DcMenu description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public DcMenu createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public DcMenu updateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean isEnable() {
        return isEnable;
    }

    public DcMenu isEnable(Boolean isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Long getParentId() {
        return parentId;
    }

    public DcMenu parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public DcMenu level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public DcMenu url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<DcDepartment> getDcDepartments() {
        return dcDepartments;
    }

    public DcMenu dcDepartments(Set<DcDepartment> dcDepartments) {
        this.dcDepartments = dcDepartments;
        return this;
    }

    public DcMenu addDcDepartment(DcDepartment dcDepartment) {
        this.dcDepartments.add(dcDepartment);
        dcDepartment.getDcMenus().add(this);
        return this;
    }

    public DcMenu removeDcDepartment(DcDepartment dcDepartment) {
        this.dcDepartments.remove(dcDepartment);
        dcDepartment.getDcMenus().remove(this);
        return this;
    }

    public void setDcDepartments(Set<DcDepartment> dcDepartments) {
        this.dcDepartments = dcDepartments;
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
        DcMenu dcMenu = (DcMenu) o;
        if (dcMenu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dcMenu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DcMenu{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createTime=" + getCreateTime() +
            ", updateTime=" + getUpdateTime() +
            ", isEnable='" + isEnable() + "'" +
            ", parentId=" + getParentId() +
            ", level=" + getLevel() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
