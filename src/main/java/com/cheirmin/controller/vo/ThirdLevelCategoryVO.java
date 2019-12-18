package com.cheirmin.controller.vo;

import java.io.Serializable;

/**
 * @Message:首页分类数据VO(第三级)
 * @Author：Cheirmin
 * @Date: 2019/12/18 10:21
 * @Version 1.0
 */
public class ThirdLevelCategoryVO implements Serializable {
    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Byte categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
