package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.UserPrivate;
import com.jiazhuo.blockgamesquare.mapper.UserPrivateMapper;
import com.jiazhuo.blockgamesquare.service.IUserPrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPrivateServiceImpl implements IUserPrivateService {
    @Autowired
    private UserPrivateMapper userPrivateMapper;

    @Override
    public UserPrivate realAuthInfo(String uid) {
        UserPrivate userPrivate = userPrivateMapper.selectByPrimaryKey(uid);
        return userPrivate;
    }
}
