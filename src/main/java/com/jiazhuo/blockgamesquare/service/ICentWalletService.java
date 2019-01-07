package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;
import com.jiazhuo.blockgamesquare.vo.WalletVo;

import java.util.List;

public interface ICentWalletService {
    /**
     * 用户中心化钱包列表
     * @param qo
     * @return
     */
    PageResult centWalletPage(UserQueryObject qo);

    /**
     * excel导出用户中心化钱包列表
     * @return
     */
    @SystemlogAnnotation("excel导出用户中心化钱包列表")
    List<WalletVo> exportCentWalletData();
}
