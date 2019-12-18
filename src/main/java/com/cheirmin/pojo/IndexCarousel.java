package com.cheirmin.pojo;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_index_carousel")
public class IndexCarousel {
    private Integer carouselId;

    private String carouselUrl;

    private String redirectUrl;

    private Integer carouselRank;

    private Byte isDeleted;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    public Integer getCarouselId() {
        return carouselId;
    }

    public void setCarouselId(Integer carouselId) {
        this.carouselId = carouselId;
    }

    public String getCarouselUrl() {
        return carouselUrl;
    }

    public void setCarouselUrl(String carouselUrl) {
        this.carouselUrl = carouselUrl == null ? null : carouselUrl.trim();
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl == null ? null : redirectUrl.trim();
    }

    public Integer getCarouselRank() {
        return carouselRank;
    }

    public void setCarouselRank(Integer carouselRank) {
        this.carouselRank = carouselRank;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}