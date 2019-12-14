package com.cheirmin.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

/**
 * @Message:错误页面控制器
 * @Author：Cheirmin
 * @Date: 2019/12/14 10:36
 * @Version 1.0
 */
public class ErrorPageController {

    private static ErrorPageController errorPageController;

    @Autowired
    private ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    public ErrorPageController(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }

    public ErrorPageController(){
        if (errorPageController == null){
            errorPageController = new ErrorPageController(errorAttributes);
        }
    }

}
