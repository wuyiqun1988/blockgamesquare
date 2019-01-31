package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.mapper.UserStatusMapper;
import com.jiazhuo.blockgamesquare.service.IUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatusServiceImpl implements IUserStatusService {
    @Autowired
    private UserStatusMapper userStatusMapper;

    @Override
    public boolean changeStatus(String UID, Byte state) {
        if (UID == null){
            return false;
        }
        userStatusMapper.changeStatus(UID, state);
        return true;
    }

    @Override
    public int queryDailyActiveUser() {
        int dailyActiveUser = userStatusMapper.queryDailyActiveUser();
        return dailyActiveUser;
    }
}
