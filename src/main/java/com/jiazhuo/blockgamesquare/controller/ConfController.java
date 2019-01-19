package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Optconf;
import com.jiazhuo.blockgamesquare.service.IOptconfService;
import com.jiazhuo.blockgamesquare.util.JedisClient;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能设置
 */
@Controller
public class ConfController {
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private IOptconfService optconfService;

    /**
     * 功能配置数据
     * @return
     */
    @RequestMapping(value = "/mgrsite/conf", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo conf(){
        JSONResultVo vo = new JSONResultVo();
        Map<String, String> map = new HashMap<>();
        List<Optconf> optconfList = optconfService.queryList();
        for (Optconf op : optconfList) {
            map.put(op.getConfName(), op.getConfValue());
        }
        vo.setResult(map);
        return vo;
    }


    /**
     * 修改配置数据
     * @param confName
     * @param confValue
     * @return
     */
    @RequestMapping(value = "/mgrsite/updateConf", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("修改配置数据")
    public JSONResultVo updateConf(String confName, String confValue){
        if (jedisClient.hget("operationCode", confName) == null){ //缓存中没有数据
            Optconf optconf = optconfService.get(confName);
            if (optconf != null){   //数据库中有数据(缓存失效)
                jedisClient.hset("operationCode", confName, confValue);
                optconfService.updateConf(confName, confValue);
                return JSONResultVo.ok("缓存失效,已重新设置");
            }else {                 //数据库中没有数据(参数错误)
                return JSONResultVo.error("无此项配置");
            }
        }
        jedisClient.hset("operationCode", confName, confValue);   //缓存中有数据
        optconfService.updateConf(confName, confValue);
        return JSONResultVo.ok("修改配置数据成功");
    }
}
