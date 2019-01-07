package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.UserPrivate;

public interface IUserPrivateService {
    /**
     * 用户实名认证信息
     * @param uid
     * @return
     */
    UserPrivate realAuthInfo(String uid);
}
