package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.LockWarehouse;
import com.jiazhuo.blockgamesquare.mapper.LockWarehouseMapper;
import com.jiazhuo.blockgamesquare.qo.LockWarehouseQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.service.ILockWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockWarehouseServiceImpl implements ILockWarehouseService {
    @Autowired
    private LockWarehouseMapper lockWarehouseMapper;

    @Override
    public PageResult lockOrderPage(LockWarehouseQueryObject qo) {
        int totalCount = lockWarehouseMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = lockWarehouseMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<LockWarehouse> exportLockOrderData() {
        return lockWarehouseMapper.exportLockOrderData();
    }

    @Override
    public PageResult unlockOrderPage(LockWarehouseQueryObject qo) {
        int totalCount = lockWarehouseMapper.queryWaitUnlockCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = lockWarehouseMapper.queryWaitUnlockList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }
}
