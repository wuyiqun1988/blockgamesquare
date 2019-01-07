package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Systemlog;
import com.jiazhuo.blockgamesquare.mapper.SystemlogMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.SystemLogQueryObject;
import com.jiazhuo.blockgamesquare.service.ISystemlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemlogServiceImpl implements ISystemlogService {
    @Autowired
    private SystemlogMapper systemlogMapper;

    @Override
    public void write(Systemlog log) {
        systemlogMapper.insert(log);
    }

    @Override
    public PageResult systemLogPage(SystemLogQueryObject qo) {
        int totalCount = systemlogMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = systemlogMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }
}
