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
     * 查询当前类型游戏的最大排序
     * @param type
     * @return
     */
    Byte selectMaxSort(Byte type);

    /**
     * 更新排序
     * @param game
     */
    void updateSort(@Param("gid") Long gid, @Param("sort") Byte sort);
}