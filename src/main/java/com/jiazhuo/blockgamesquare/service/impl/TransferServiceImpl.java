package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Transfer;
import com.jiazhuo.blockgamesquare.mapper.TransferMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.WalletItemQueryObject;
import com.jiazhuo.blockgamesquare.service.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements ITransferService {
    @Autowired
    private TransferMapper transferMapper;

    @Override
    public PageResult chainWalletItemPage(WalletItemQueryObject qo) {
        int totalCount = transferMapper.queryChainCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = transferMapper.queryChainList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public PageResult centWalletItemPage(WalletItemQueryObject qo) {
        int totalCount = transferMapper.queryCentCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = transferMapper.queryCentList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<Transfer> exportChainWalletItemData() {
        return transferMapper.queryChainWalletItem();
    }

    @Override
    public List<Transfer> exportCentWalletItemData() {
        return transferMapper.queryCentWalletItem();
    }
}
