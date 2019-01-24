package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.WithdrawMoney;
import com.jiazhuo.blockgamesquare.mapper.WithdrawMoneyMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.WithdrawMoneyQueryObject;
import com.jiazhuo.blockgamesquare.service.IWithdrawMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawMoneyServiceImpl implements IWithdrawMoneyService {
    @Autowired
    private WithdrawMoneyMapper withdrawMoneyMapper;

    @Override
    public PageResult withdrawMoneyApplyPage(WithdrawMoneyQueryObject qo) {
        int totalCount = withdrawMoneyMapper.queryApplyCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = withdrawMoneyMapper.queryApplyList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public PageResult withdrawMoneyPassPage(WithdrawMoneyQueryObject qo) {
        int totalCount = withdrawMoneyMapper.queryPassCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = withdrawMoneyMapper.queryPassList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public PageResult withdrawMoneyFailPage(WithdrawMoneyQueryObject qo) {
        int totalCount = withdrawMoneyMapper.queryFailCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = withdrawMoneyMapper.queryFailList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<WithdrawMoney> exportWithdrawMoneyPassData() {
        return withdrawMoneyMapper.exportWithdrawMoneyPassData();
    }

    @Override
    public List<WithdrawMoney> exportWithdrawMoneyFailData() {
        return withdrawMoneyMapper.exportWithdrawMoneyFailData();
    }

    @Override
    public void recordWithdrawMoneyAuditSystemLog() {
        System.out.println("记录提现审核操作成功");
    }
}
