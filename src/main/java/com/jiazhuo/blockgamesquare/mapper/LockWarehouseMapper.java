package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.domain.LockWarehouse;
import java.util.List;

import com.jiazhuo.blockgamesquare.qo.LockWarehouseQueryObject;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

public interface LockWarehouseMapper {
    int deleteByPrimaryKey(@Param("LID") Long LID, @Param("UID") String UID);

    int insert(LockWarehouse record);

    LockWarehouse selectByPrimaryKey(@Param("LID") Long LID, @Param("UID") String UID);

    List<LockWarehouse> selectAll();

    int updateByPrimaryKey(LockWarehouse record);

    int queryCount(QueryObject qo);

    List<LockWarehouse> queryList(QueryObject qo);

    List<LockWarehouse> exportLockOrderData();

    int queryWaitUnlockCount(LockWarehouseQueryObject qo);

    List<LockWarehouse> queryWaitUnlockList(LockWarehouseQueryObject qo);
}