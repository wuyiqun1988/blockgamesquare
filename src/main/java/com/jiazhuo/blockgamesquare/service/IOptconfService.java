package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Optconf;

import java.util.List;

public interface IOptconfService {
    List<Optconf> queryList();

    Optconf get(String confName);

    void updateConf(String confName, String confValue);
}
