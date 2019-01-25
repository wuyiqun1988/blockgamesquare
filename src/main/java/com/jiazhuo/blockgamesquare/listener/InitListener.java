package com.jiazhuo.blockgamesquare.listener;

import com.jiazhuo.blockgamesquare.service.IBgUserService;
import com.jiazhuo.blockgamesquare.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 初始化第一个管理员
 * 初始化菜单和权限的关系
 * 使用spring的事件机制
 * 监听所有的bean都已经加载完毕之后的事件
 */
@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private IBgUserService bgUserService;
    @Autowired
    private IMenuService menuService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        bgUserService.initAdmin();
        menuService.initMenuPermission();
    }
}
