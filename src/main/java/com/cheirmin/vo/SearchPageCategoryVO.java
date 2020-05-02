package com.cheirmin.vo;

import com.cheirmin.pojo.BooksCategory;

import java.io.Serializable;
import java.util.List;

/**
 * @Message:搜索页面分类数据VO
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:20
 * @Version 1.0
 */
public class SearchPageCategoryVO implements Serializable {
    private String firstLevelCategoryName;

    private List<BooksCategory> secondLevelCategoryList;

    private String secondLevelCategoryName;

    private List<BooksCategory> thirdLevelCategoryList;

    private String currentCategoryName;

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public List<BooksCategory> getSecondLevelCategoryList() {
        return secondLevelCategoryList;
    }

    public void setSecondLevelCategoryList(List<BooksCategory> secondLevelCategoryList) {
        this.secondLevelCategoryList = secondLevelCategoryList;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public List<BooksCategory> getThirdLevelCategoryList() {
        return thirdLevelCategoryList;
    }

    public void setThirdLevelCategoryList(List<BooksCategory> thirdLevelCategoryList) {
        this.thirdLevelCategoryList = thirdLevelCategoryList;
    }

    public String getCurrentCategoryName() {
        return currentCategoryName;
    }

    public void setCurrentCategoryName(String currentCategoryName) {
        this.currentCategoryName = currentCategoryName;
    }
}
