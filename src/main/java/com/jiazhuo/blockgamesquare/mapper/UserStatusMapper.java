package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.UserStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserStatusMapper {
    int deleteByPrimaryKey(String UID);

    int insert(UserStatus record);

    UserStatus selectByPrimaryKey(String UID);

    List<UserStatus> selectAll();

    int updateByPrimaryKey(UserStatus record);

    void changeStatus(@Param("UID") String uid, @Param("state") Byte state);

    String selectInviteTime(String UID);
}