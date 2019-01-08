package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

public interface IUserStatusService {
    /**
     * 改变用户的状态(正常/禁用)
     * @param UID
     * @param state
     * @return
     */
    @SystemlogAnnotation("改变用户的状态(正常/禁用)")
    boolean changeStatus(String UID, Byte state);
}
