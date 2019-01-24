package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Optconf;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface IOptconfService {
    List<Optconf> queryList();

    Optconf get(String confName);
    @SystemlogAnnotation("修改配置数据")
    void updateConf(String confName, String confValue);

    /**
     * 记录回收钱包系统日志
     */
    @SystemlogAnnotation("回收钱包")
    void recordRecycleEOSWalletSystemLog();
}
