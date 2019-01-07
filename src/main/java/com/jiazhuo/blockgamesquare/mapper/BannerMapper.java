package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Banner;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    int deleteByPrimaryKey(Long bid);

    int insert(Banner record);

    Banner selectByPrimaryKey(Long bid);

    List<Banner> selectAll();

    int updateByPrimaryKey(Banner record);

    int queryCount(QueryObject qo);

    List<Banner> queryList(QueryObject qo);

    void changeStatus(@Param("bid") Long bid, @Param("status") Byte status);
}