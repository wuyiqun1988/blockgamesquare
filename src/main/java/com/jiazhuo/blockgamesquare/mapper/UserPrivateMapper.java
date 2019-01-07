package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.UserPrivate;
import java.util.List;

public interface UserPrivateMapper {
    int deleteByPrimaryKey(String UID);

    int insert(UserPrivate record);

    UserPrivate selectByPrimaryKey(String UID);

    List<UserPrivate> selectAll();

    int updateByPrimaryKey(UserPrivate record);
}