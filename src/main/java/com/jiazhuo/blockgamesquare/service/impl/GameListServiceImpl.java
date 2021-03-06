package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.mapper.GameListMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IGameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameListServiceImpl implements IGameListService {
    @Autowired
    private GameListMapper gameListMapper;

    @Override
    public PageResult gamePage(QueryObject qo) {
        int totalCount = gameListMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = gameListMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(GameList gameList) {
        if (gameList.getGid() != null){
            gameListMapper.updateByPrimaryKey(gameList);
        } else {
            Byte maxSort = gameListMapper.selectMaxSort(gameList.getType());
            //设置排序序号
            gameList.setSort((byte) (maxSort + 1));
            gameList.setJoindate(new Date());
            gameListMapper.insert(gameList);
        }
    }

    @Override
    public void changeStatus(Long gid, Byte status) {
        gameListMapper.changeStatus(gid, status);
    }

    @Override
    public void updateSort(Long gid1, Long gid2) {
        GameList game1 = gameListMapper.selectByPrimaryKey(gid1);
        GameList game2 = gameListMapper.selectByPrimaryKey(gid2);
        //互换两个游戏的排序序号
        Byte temp = game1.getSort();
        game1.setSort(game2.getSort());
        game2.setSort(temp);
        gameListMapper.updateSort(game1.getGid(), game1.getSort());
        gameListMapper.updateSort(game2.getGid(), game2.getSort());
    }

}
