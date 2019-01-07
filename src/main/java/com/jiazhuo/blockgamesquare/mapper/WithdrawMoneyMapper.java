package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.WithdrawMoney;
import java.util.List;
import com.jiazhuo.blockgamesquare.qo.WithdrawMoneyQueryObject;
import org.apache.ibatis.annotations.Param;

public interface WithdrawMoneyMapper {
    int deleteByPrimaryKey(@Param("UID") String UID, @Param("WID") String WID);

    int insert(WithdrawMoney record);

    WithdrawMoney selectByPrimaryKey(@Param("UID") String UID, @Param("WID") String WID);

    int updateByPrimaryKey(WithdrawMoney record);

    int queryApplyCount(WithdrawMoneyQueryObject qo);

    List<WithdrawMoney> queryApplyList(WithdrawMoneyQueryObject qo);

    int queryPassCount(WithdrawMoneyQueryObject qo);

    List<WithdrawMoney> queryPassList(WithdrawMoneyQueryObject qo);

    int queryFailCount(WithdrawMoneyQueryObject qo);

    List<WithdrawMoney> queryFailList(WithdrawMoneyQueryObject qo);

    List<WithdrawMoney> exportWithdrawMoneyPassData();

    List<WithdrawMoney> exportWithdrawMoneyFailData();
}