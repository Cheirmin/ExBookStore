package com.cheirmin.common;

/**
 * @Message:首页配置
 * @Author：Cheirmin
 * @Date: 2019/12/13 16:03
 * @Version 1.0
 */
public enum IndexConfigTypeEnum {
    DEFAULT(0, "DEFAULT"),
    INDEX_SEARCH_HOTS(1, "INDEX_SEARCH_HOTS"),
    INDEX_SEARCH_DOWN_HOTS(2, "INDEX_SEARCH_DOWN_HOTS"),
    INDEX_BOOKS_HOT(3, "INDEX_BOOKS_HOTS"),
    INDEX_BOOKS_NEW(4, "INDEX_BOOKS_NEW"),
    INDEX_BOOKS_RECOMMOND(5, "INDEX_BOOKS_RECOMMOND");

    private int type;

    private String name;

    IndexConfigTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static IndexConfigTypeEnum getIndexConfigTypeEnumByType(int type) {
        for (IndexConfigTypeEnum indexConfigTypeEnum : IndexConfigTypeEnum.values()) {
            if (indexConfigTypeEnum.getType() == type) {
                return indexConfigTypeEnum;
            }
        }
        return DEFAULT;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
