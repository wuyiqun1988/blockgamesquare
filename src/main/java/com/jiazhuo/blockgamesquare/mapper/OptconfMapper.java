package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Optconf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OptconfMapper {
    int deleteByPrimaryKey(String confName);
    int insert(Optconf record);
    Optconf selectByPrimaryKey(String confName);
    List<Optconf> selectAll();

    void update(@Param("confName") String confName, @Param("confValue") String confValue);

}