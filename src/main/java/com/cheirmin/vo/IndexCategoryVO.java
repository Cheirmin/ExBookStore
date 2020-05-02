package com.cheirmin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Message:首页分类数据VO
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:14
 * @Version 1.0
 */
public class IndexCategoryVO implements Serializable {
    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;

    private List<SecondLevelCategoryVO> secondLevelCategoryVOS;

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

    public List<SecondLevelCategoryVO> getSecondLevelCategoryVOS() {
        return secondLevelCategoryVOS;
    }

    public void setSecondLevelCategoryVOS(List<SecondLevelCategoryVO> secondLevelCategoryVOS) {
        this.secondLevelCategoryVOS = secondLevelCategoryVOS;
    }
}
