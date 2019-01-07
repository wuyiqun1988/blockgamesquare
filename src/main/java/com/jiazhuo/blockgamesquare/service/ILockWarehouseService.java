package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.LockWarehouse;
import com.jiazhuo.blockgamesquare.qo.LockWarehouseQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface ILockWarehouseService {
    PageResult lockOrderPage(LockWarehouseQueryObject qo);

    /**
     * excel导出锁仓订单列表
     * @return
     */
    @SystemlogAnnotation("excel导出锁仓订单列表")
    List<LockWarehouse> exportLockOrderData();

    /**
     * 归仓中列表(等待解仓)
     * @param qo
     * @return
     */
    PageResult unlockOrderPage(LockWarehouseQueryObject qo);
}
