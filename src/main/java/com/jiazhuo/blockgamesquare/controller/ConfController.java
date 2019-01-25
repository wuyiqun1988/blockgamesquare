package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Optconf;
import com.jiazhuo.blockgamesquare.service.IOptconfService;
import com.jiazhuo.blockgamesquare.util.HttpClientUtil;
import com.jiazhuo.blockgamesquare.util.JedisClient;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
        List<Optconf> optconfList = optconfService.queryList();
        for (Optconf op : optconfList) {
            switch (op.getConfName()){
                case "MAX_DAILY_INVITING_COUNT":
                    op.setExplain("每日邀请好友上限");
                    break;
                case "MINI_LOCK_BGS":
                    op.setExplain("锁仓最低额限制(BGS)");
                    break;
                case "MINI_LOCK_EOS":
                    op.setExplain("锁仓最低额限制(EOS)");
                    break;
                case "MINI_LOCK_ETH":
                    op.setExplain("锁仓最低额限制(ETH)");
                    break;
                case "MINI_TRANSFER_BGS":
                    op.setExplain("转出最低额限制(BGS)");
                    break;
                case "MINI_TRANSFER_EOS":
                    op.setExplain("转出最低额限制(EOS)");
                    break;
                case "MINI_TRANSFER_ETH":
                    op.setExplain("转出最低额限制(ETH)");
                    break;
                case "MINI_WITHDRAW_BGS":
                    op.setExplain("提现最低限额(BGS)");
                    break;
                case "MINI_WITHDRAW_EOS":
                    op.setExplain("提现最低限额(EOS)");
                    break;
                case "MINI_WITHDRAW_ETH":
                    op.setExplain("提现最低限额(ETH)");
                    break;
                case "PRICE_BUYAGENT":
                    op.setExplain("购买代理人价格");
                    break;
                case "PROFITWEIGHT_30":
                    op.setExplain("30天锁仓的权重(对于7天)");
                    break;
                case "PROFITWEIGHT_90":
                    op.setExplain("90天锁仓的权重(对于7天)");
                    break;
                case "PROFIT_BGS_LOCK_07":
                    op.setExplain("7天锁仓返回BGS数");
                    break;
                case "PROFIT_BGS_LOCK_30":
                    op.setExplain("30天锁仓返回BGS数");
                    break;
                case "PROFIT_BGS_LOCK_90":
                    op.setExplain("90天锁仓返回BGS数");
                    break;
                case "PROFIT_DAPP_AGENT":
                    op.setExplain("Dapp返现分配(代理人分配比例)");
                    break;
                case "PROFIT_DAPP_LOCK":
                    op.setExplain("Dapp返现分配(锁仓分配比例)");
                    break;
                case "PROFIT_DAPP_PLATFORM":
                    op.setExplain("Dapp返现分配(平台利润分配比例)");
                    break;
                case "PROFIT_INVITING_BGS":
                    op.setExplain("邀请好友奖励BGS数量");
                    break;
                case "PROFIT_REGISTER_BGS":
                    op.setExplain("注册奖励BGS数量");
                    break;
                case "RECYCLE_INTERVALTIME":
                    op.setExplain("EOS钱包回收时间间隔");
                    break;
                case "RECYCLE_MIN_AMOUNT":
                    op.setExplain("EOS钱包回收限额");
                    break;
                case "TOKENPRICE_BGS":
                    op.setExplain("BGS汇率");
                    break;
                case "TOKENPRICE_EOS":
                    op.setExplain("EOS汇率");
                    break;
                case "TOKENPRICE_ETH":
                    op.setExplain("ETH汇率");
                    break;
                case "TRX_CPU_USER":
                    op.setExplain("质押CPU的EOS量");
                    break;
                case "TRX_NET_USER":
                    op.setExplain("质押NET的EOS量");
                    break;
                case "URL":
                    op.setExplain("二维码前缀");
                    break;
                case "BGS_GAS_MAX":
                    op.setExplain("转BGS消耗gas费上限");
                    break;
                case "BGS_GAS_MIN":
                    op.setExplain("转BGS消耗gas费下限");
                    break;
                case "ETH_GAS_MAX":
                    op.setExplain("转ETH消耗gas费上限");
                    break;
                case "ETH_GAS_MIN":
                    op.setExplain("转ETH消耗gas费下限");
                    break;
            }
        }
        vo.setResult(optconfList);
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

    /**
     * 查询gas配置
     * @return
     */
    @RequestMapping(value = "/appsite/gasConf", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult gasConf(){
        List<Optconf> confs = optconfService.queryList();
        List<Map<String, Object>> gasConf = new ArrayList<>();
        Map<String, Object> eth = new HashMap<>();
        Map<String, Object> bgs = new HashMap<>();
        for (Optconf op : confs) {
            if (op.getConfName().equals("BGS_GAS_MAX")){
                Double i = Double.parseDouble(op.getConfValue());
                bgs.put("type", "3");
                bgs.put("max", i);
            }
            if (op.getConfName().equals("BGS_GAS_MIN")){
                Double i = Double.parseDouble(op.getConfValue());
                bgs.put("min", i);
            }
            if (op.getConfName().equals("ETH_GAS_MAX")){
                Double i = Double.parseDouble(op.getConfValue());
                eth.put("type", "1");
                eth.put("max", i);
            }
            if (op.getConfName().equals("ETH_GAS_MIN")){
                Double i = Double.parseDouble(op.getConfValue());
                eth.put("min", i);
            }
        }
        gasConf.add(eth);
        gasConf.add(bgs);
        SuperResult res = SuperResult.ok(gasConf);
        res.setMsg("请求gas配置成功");
        return res;
    }

    @RequestMapping(value = "/mgrsite/recycleEOSWallet", method = RequestMethod.POST)
    @ResponseBody
    public String recycleEOSWallet(){
        //请求服务器回收钱包接口
        String url =  HttpClientUtil.HOST_POST + HttpClientUtil.RECYCLE_WALLET;
        String result = HttpClientUtil.doPost(url);
        //记录系统日志
        optconfService.recordRecycleEOSWalletSystemLog();
        return result;
    }
}
