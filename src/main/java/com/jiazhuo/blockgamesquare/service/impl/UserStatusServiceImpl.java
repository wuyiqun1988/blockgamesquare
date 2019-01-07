package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.mapper.UserStatusMapper;
import com.jiazhuo.blockgamesquare.service.IUserStatusService;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatusServiceImpl implements IUserStatusService {
    @Autowired
    private UserStatusMapper userStatusMapper;

    @Override
    public void changeStatus(String UID, Byte state) {
        if (UID == null){
            throw new DisplayableException("请选择需要操作的用户");
        }
        userStatusMapper.changeStatus(UID, state);
    }
}
