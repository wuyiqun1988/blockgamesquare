package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Systemlog;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.SystemLogQueryObject;

public interface ISystemlogService {
    /**
     * 写日志
     * @param log
     */
    void write(Systemlog log);

    PageResult systemLogPage(SystemLogQueryObject qo);
}
