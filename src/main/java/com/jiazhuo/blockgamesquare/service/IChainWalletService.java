package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;
import com.jiazhuo.blockgamesquare.vo.WalletVo;

import java.util.List;

public interface IChainWalletService {
    PageResult chainWalletPage(UserQueryObject qo);

    /**
     * excel导出用户链上钱包列表
     * @return
     */
    @SystemlogAnnotation("excel导出用户链上钱包列表")
    List<WalletVo> exportChainWalletData();
}
