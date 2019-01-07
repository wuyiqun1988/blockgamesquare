package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

public interface IUserStatusService {
    /**
     * 改变用户的状态(正常/禁用)
     * @param uid
     */
    @SystemlogAnnotation("改变用户的状态(正常/禁用)")
    void changeStatus(String UID, Byte state);
}
