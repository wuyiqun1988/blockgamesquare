package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.domain.Notification;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {
    int deleteByPrimaryKey(Long nid);

    int insert(Notification record);

    Notification selectByPrimaryKey(Long nid);

    List<Notification> selectAll();

    int updateByPrimaryKey(Notification record);

    int queryCount(@Param("qo") QueryObject qo, @Param("UID") String UID);

    List<Notification> queryList(@Param("qo") QueryObject qo, @Param("UID") String UID);
}