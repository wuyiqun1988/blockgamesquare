package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.mapper.NotificationMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public PageResult notifications(QueryObject qo, String UID) {
        int totalCount = notificationMapper.queryCount(qo, UID);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = notificationMapper.queryList(qo, UID);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }
}
