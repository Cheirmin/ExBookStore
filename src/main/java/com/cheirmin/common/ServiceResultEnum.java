package com.cheirmin.common;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 16:11
 * @Version 1.0
 */
public enum ServiceResultEnum {
    ERROR("error"),

    SUCCESS("success"),

    DATA_NOT_EXIST("未查询到记录！"),

    SAME_CATEGORY_EXIST("有同级同名的分类！"),

    SAME_LOGIN_NAME_EXIST("用户名已存在！"),

    SAME_REGISTER_EMAIL_EXIST("该邮箱已经被注册！"),

    LOGIN_NAME_NULL("请输入登录名！"),

    REGISTER_EMAIL_NULL("请输入注册邮箱！"),

    VERIFY_CODE_NOT_SEND("验证码未发送！"),

    VERIFY_CODE_NOT_TRUE("验证码不正确！"),

    LOGIN_PASSWORD_NULL("请输入密码！"),

    LOGIN_VERIFY_CODE_NULL("请输入验证码！"),

    LOGIN_VERIFY_CODE_ERROR("验证码错误！"),

    GOODS_NOT_EXIST("商品不存在！"),

    SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR("超出单个商品的最大购买数量！"),

    LOGIN_ERROR("登录失败！"),

    LOGIN_USER_LOCKED("用户已被禁止登录！"),

    ORDER_NOT_EXIST_ERROR("订单不存在！"),

    NULL_ADDRESS_ERROR("地址不能为空！"),

    ORDER_PRICE_ERROR("订单价格异常！"),

    ORDER_GENERATE_ERROR("生成订单异常！"),

    SHOPPING_ITEM_ERROR("购物车数据异常！"),

    SHOPPING_ITEM_COUNT_ERROR("库存不足！"),

    ORDER_STATUS_ERROR("订单状态异常！"),

    OPERATE_ERROR("操作失败！"),

    DB_ERROR("database error");

    private String result;

    ServiceResultEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
