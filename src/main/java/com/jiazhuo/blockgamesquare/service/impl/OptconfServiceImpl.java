package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Optconf;
import com.jiazhuo.blockgamesquare.mapper.OptconfMapper;
import com.jiazhuo.blockgamesquare.service.IOptconfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptconfServiceImpl implements IOptconfService {
    @Autowired
    private OptconfMapper optconfMapper;

    @Override
    public List<Optconf> queryList() {
        return optconfMapper.selectAll();
    }

    @Override
    public Optconf get(String confName) {
        return optconfMapper.selectByPrimaryKey(confName);
    }

    @Override
    public void updateConf(String confName, String confValue) {
        optconfMapper.update(confName, confValue);
    }
}
