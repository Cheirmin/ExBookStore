package com.cheirmin.common;

/**
 * @Message:自定义异常
 * @Author：Cheirmin
 * @Date: 2019/12/13 16:06
 * @Version 1.0
 */
public class ExBookStoreException extends RuntimeException{

    public ExBookStoreException(){

    }

    public ExBookStoreException(String message){
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new ExBookStoreException(message);
    }

}
