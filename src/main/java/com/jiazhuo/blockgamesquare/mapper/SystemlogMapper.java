package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Systemlog;
import com.jiazhuo.blockgamesquare.qo.SystemLogQueryObject;
import java.util.List;

public interface SystemlogMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(Systemlog record);

    Systemlog selectByPrimaryKey(Long sid);

    List<Systemlog> selectAll();

    int updateByPrimaryKey(Systemlog record);

    int queryCount(SystemLogQueryObject qo);

    List<Systemlog> queryList(SystemLogQueryObject qo);
}