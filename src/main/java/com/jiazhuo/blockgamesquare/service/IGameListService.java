package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.qo.GameQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

public interface IGameListService {

    PageResult gamePage(QueryObject qo);

    @SystemlogAnnotation("游戏新增或保存")
    void saveOrUpdate(GameList gameList);
    @SystemlogAnnotation("游戏上架或下架")
    void changeStatus(Long gid, Byte status);

    /**
     * 更改置顶排序
     * @param gid1
     * @param gid2
     */
    @SystemlogAnnotation("更改置顶排序")
    void updateSort(Long gid1, Long gid2);

}
