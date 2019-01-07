package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;

public interface INotificationService {
    PageResult notifications(QueryObject qo, String UID);
}
