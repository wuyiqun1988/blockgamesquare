package com.jiazhuo.blockgamesquare.listener;

import com.jiazhuo.blockgamesquare.service.IBgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 初始化第一个管理员
 * 使用spring的事件机制
 * 监听所有的bean都已经加载完毕之后的事件
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private IBgUserService bgUserService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        bgUserService.initAdmin();
    }
}
