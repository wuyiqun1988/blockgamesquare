package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.mapper.WalletMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.service.ICentWalletService;
import com.jiazhuo.blockgamesquare.vo.WalletVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentWalletServiceImpl implements ICentWalletService {
    @Autowired
    private WalletMapper walletMapper;

    @Override
    public PageResult centWalletPage(UserQueryObject qo) {
        int totalCount = walletMapper.queryCentCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = walletMapper.queryCentList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<WalletVo> exportCentWalletData() {
        List data = walletMapper.queryCentWalletList();
        return data;
    }
}
