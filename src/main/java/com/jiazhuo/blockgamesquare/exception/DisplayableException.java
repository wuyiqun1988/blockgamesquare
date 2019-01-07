package com.jiazhuo.blockgamesquare.exception;

/**
 * 前台可以显示的异常,供用户看到
 */
public class DisplayableException extends RuntimeException {
    public DisplayableException(String message) {
        super(message);
    }
}
