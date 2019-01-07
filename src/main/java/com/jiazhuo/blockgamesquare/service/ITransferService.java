package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Transfer;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.WalletItemQueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface ITransferService {
    /**
     * 链上钱包交易明细
     * @param qo
     * @return
     */
    PageResult chainWalletItemPage(WalletItemQueryObject qo);

    /**
     * 中心化钱包交易明细
     * @param qo
     * @return
     */
    PageResult centWalletItemPage(WalletItemQueryObject qo);

    /**
     * excel导出用户链上钱包交易明细
     * @return
     */
    @SystemlogAnnotation("excel导出用户链上钱包交易明细")
    List<Transfer> exportChainWalletItemData();

    /**
     * excel导出用户中心化钱包交易明细
     * @return
     */
    @SystemlogAnnotation("excel导出用户中心化钱包交易明细")
    List<Transfer> exportCentWalletItemData();
}
