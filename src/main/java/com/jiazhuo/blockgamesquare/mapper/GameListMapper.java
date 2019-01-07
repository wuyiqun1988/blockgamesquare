package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameListMapper {
    int deleteByPrimaryKey(Long gid);

    int insert(GameList record);

    GameList selectByPrimaryKey(Long gid);

    List<GameList> selectAll();

    int updateByPrimaryKey(GameList record);

    int queryCount(QueryObject qo);

    List<GameList> queryList(QueryObject qo);

    void changeStatus(@Param("gid") Long gid, @Param("status") Byte status);

    /**
     * 设置游戏的排序序号
     * @param gid
     * @param no
     */
    void setSort(@Param("gid") Long gid, @Param("no") Byte no);
}