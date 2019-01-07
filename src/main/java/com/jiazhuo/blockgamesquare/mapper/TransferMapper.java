package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Transfer;
import java.util.List;

import com.jiazhuo.blockgamesquare.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

public interface TransferMapper {
    int deleteByPrimaryKey(@Param("transferId") Long transferId, @Param("UID") String UID);

    int insert(Transfer record);

    Transfer selectByPrimaryKey(@Param("transferId") Long transferId, @Param("UID") String UID);

    int updateByPrimaryKey(Transfer record);

    int queryChainCount(QueryObject qo);

    List<Transfer> queryChainList(QueryObject qo);

    int queryCentCount(QueryObject qo);

    List<Transfer> queryCentList(QueryObject qo);

    /**
     * 查询链上钱包交易明细
     * @return
     */
    List<Transfer> queryChainWalletItem();

    /**
     * 查询中心化钱包交易明细
     * @return
     */
    List<Transfer> queryCentWalletItem();
}