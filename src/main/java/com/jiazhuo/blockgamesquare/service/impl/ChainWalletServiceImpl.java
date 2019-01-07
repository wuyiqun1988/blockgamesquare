package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.mapper.WalletMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.service.IChainWalletService;
import com.jiazhuo.blockgamesquare.vo.WalletVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChainWalletServiceImpl implements IChainWalletService {
    @Autowired
    private WalletMapper walletMapper;

    @Override
    public PageResult chainWalletPage(UserQueryObject qo) {
        int totalCount = walletMapper.queryChainCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = walletMapper.queryChainList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<WalletVo> exportChainWalletData() {
        List data = walletMapper.queryChainWalletList();
        return data;
    }
}
