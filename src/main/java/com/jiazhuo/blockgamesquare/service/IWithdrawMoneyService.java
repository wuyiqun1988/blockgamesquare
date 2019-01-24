package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.WithdrawMoney;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.WithdrawMoneyQueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface IWithdrawMoneyService {
    /**
     * 提现申请列表
     * @param qo
     * @return
     */
    PageResult withdrawMoneyApplyPage(WithdrawMoneyQueryObject qo);

    /**
     * 提现审核成功列表
     * @param qo
     * @return
     */
    PageResult withdrawMoneyPassPage(WithdrawMoneyQueryObject qo);

    /**
     * 提现审核失败列表
     * @param qo
     * @return
     */
    PageResult withdrawMoneyFailPage(WithdrawMoneyQueryObject qo);

    /**
     * excel导出提现审核通过列表
     * @return
     */
    @SystemlogAnnotation("excel导出提现审核通过列表")
    List<WithdrawMoney> exportWithdrawMoneyPassData();

    /**
     * excel导出提现审核失败列表
     * @return
     */
    @SystemlogAnnotation("excel导出提现审核失败列表")
    List<WithdrawMoney> exportWithdrawMoneyFailData();

    /**
     * 记录提现审核的系统日志
     */
    @SystemlogAnnotation("提现审核")
    void recordWithdrawMoneyAuditSystemLog();
}
