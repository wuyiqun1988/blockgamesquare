package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.qo.UserQueryObject;

import java.util.List;

public interface WalletMapper {

    int queryChainCount(UserQueryObject qo);

    List queryChainList(UserQueryObject qo);

    int queryCentCount(UserQueryObject qo);

    List queryCentList(UserQueryObject qo);

    /**
     * 查询链上钱包列表(不分页)
     * @return
     */
    List queryChainWalletList();

    /**
     * 查询中心化钱包列表(不分页)
     * @return
     */
    List queryCentWalletList();
}