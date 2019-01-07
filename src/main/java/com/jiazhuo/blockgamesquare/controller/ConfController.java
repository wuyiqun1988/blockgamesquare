package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.util.JedisClient;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能设置
 */
@Controller
public class ConfController {
    @Autowired
    private JedisClient jedisClient;

    /**
     * 功能配置数据
     * @return
     */
    @RequestMapping(value = "/mgrsite/conf", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo conf(){
        JSONResultVo vo = new JSONResultVo();
        Map<String, Object> map = new HashMap<>();
        //转出最低额限制
        String miniTransferETH = jedisClient.hget("operationCode", "MINI_TRANSFER_ETH");//ETH
        String miniTransferEOS = jedisClient.hget("operationCode", "MINI_TRANSFER_EOS");//EOS
        String miniTransferBGS = jedisClient.hget("operationCode", "MINI_TRANSFER_BGS");//BGS
        map.put("miniTransferETH", miniTransferETH);
        map.put("miniTransferEOS", miniTransferEOS);
        map.put("miniTransferBGS", miniTransferBGS);
        //锁仓最低额限制
        String miniLockETH = jedisClient.hget("operationCode", "MINI_LOCK_ETH");//ETH
        String miniLockEOS = jedisClient.hget("operationCode", "MINI_LOCK_EOS");//EOS
        String miniLockBGS = jedisClient.hget("operationCode", "MINI_LOCK_BGS");//BGS
        map.put("miniLockETH", miniLockETH);
        map.put("miniLockEOS", miniLockEOS);
        map.put("miniLockBGS", miniLockBGS);
        //锁仓收益权重-- 30/90天对7天
        String profitWeight30 = jedisClient.hget("operationCode", "PROFITWEIGHT_30");//30天锁仓的权重
        String profitWeight90 = jedisClient.hget("operationCode", "PROFITWEIGHT_90");//90天锁仓的权重
        map.put("profitWeight30", profitWeight30);
        map.put("profitWeight90", profitWeight90);
        //锁仓对应每天返回的BGS数量
        String profitBGSLock07 = jedisClient.hget("operationCode", "PROFIT_BGS_LOCK_07");//7天锁仓返回BGS数
        String profitBGSLock30 = jedisClient.hget("operationCode", "PROFIT_BGS_LOCK_30");//30天锁仓返回BGS数
        String profitBGSLock90 = jedisClient.hget("operationCode", "PROFIT_BGS_LOCK_90");//90天锁仓返回BGS数
        map.put("profitBGSLock07", profitBGSLock07);
        map.put("profitBGSLock30", profitBGSLock30);
        map.put("profitBGSLock90", profitBGSLock90);
        //提现最低限额
        String miniWithdrawETH = jedisClient.hget("operationCode", "MINI_WITHDRAW_ETH");//ETH
        String miniWithdrawEOD = jedisClient.hget("operationCode", "MINI_WITHDRAW_EOS");//EOS
        String miniWithdrawBGS = jedisClient.hget("operationCode", "MINI_WITHDRAW_BGS");//BGS
        map.put("miniWithdrawETH", miniWithdrawETH);
        map.put("miniWithdrawEOS", miniWithdrawEOD);
        map.put("miniWithdrawBGS", miniWithdrawBGS);
        //代理人价格
        String priceBuyAgentBGS = jedisClient.hget("operationCode", "PRICE_BUYAGENT_BGS");//BGS
        map.put("priceBuyAgentBGS", priceBuyAgentBGS);
        //每日邀请好友上限
        String maxDailyInvitingCount = jedisClient.hget("operationCode", "MAX_DAILY_INVITING_COUNT");//邀请数量
        map.put("maxDailyInvitingCount", maxDailyInvitingCount);
        //邀请好友奖励BGS数量
        String profitInvitingBGS = jedisClient.hget("operationCode", "PROFIT_INVITING_BGS");//BGS
        map.put("profitInvitingBGS", profitInvitingBGS);
        //Dapp返现分配
        String profitDappLock = jedisClient.hget("operationCode", "PROFIT_DAPP_LOCK");//锁仓分配比例
        String profitDappAgent = jedisClient.hget("operationCode", "PROFIT_DAPP_AGENT");//代理人分配比例
        String profitDappPlatform = jedisClient.hget("operationCode", "PROFIT_DAPP_PLATFORM");//平台利润分配比例
        map.put("profitDappLock", profitDappLock);
        map.put("profitDappAgent", profitDappAgent);
        map.put("profitDappPlatform", profitDappPlatform);
        vo.setResult(map);
        return vo;
    }

    /**
     * 功能配置 转出最低限额
     * @return
     */
    @RequestMapping(value = "/mgrsite/transferLimit", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo transferLimit(String ETHTransferLimit, String EOSTransferLimit, String BGSTransferLimit){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "MINI_TRANSFER_ETH", ETHTransferLimit);
        jedisClient.hset("operationCode", "MINI_TRANSFER_EOS", EOSTransferLimit);
        jedisClient.hset("operationCode", "MINI_TRANSFER_BGS", BGSTransferLimit);
        vo.setResult("修改转出最低限额成功");
        return vo;
    }

    /**
     * 功能配置 锁仓最低限额
     * @return
     */
    @RequestMapping(value = "/mgrsite/lockConf/miniLock", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo lockConf(String miniLockETH, String miniLockEOS, String miniLockBGS){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "MINI_LOCK_ETH", miniLockETH);
        jedisClient.hset("operationCode", "MINI_LOCK_EOS", miniLockEOS);
        jedisClient.hset("operationCode", "MINI_LOCK_BGS", miniLockBGS);
        vo.setResult("修改转出最低限额成功");
        return vo;
    }

    /**
     * 功能设置 锁仓收益权重
     * @return
     */
    @RequestMapping(value = "/mgrsite/lockConf/profitWeight", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo profitWeight(String profitWeight30, String profitWeight90){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "PROFITWEIGHT_30", profitWeight30);
        jedisClient.hset("operationCode", "PROFITWEIGHT_90", profitWeight90);
        vo.setResult("修改锁仓收益权重成功");
        return vo;
    }

    /**
     * 功能设置 锁仓对应每天返回的BGS数量
     * @return
     */
    @RequestMapping(value = "/mgrsite/lockConf/profitBGSLock", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo profitBGSLock(String profitBGSLock07, String profitBGSLock30, String profitBGSLock90){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "PROFIT_BGS_LOCK_07", profitBGSLock07);
        jedisClient.hset("operationCode", "PROFIT_BGS_LOCK_30", profitBGSLock30);
        jedisClient.hset("operationCode", "PROFIT_BGS_LOCK_90", profitBGSLock90);
        vo.setResult("修改锁仓对应每天返回BGS数量成功");
        return vo;
    }

    /**
     * 功能设置 提现最低限额
     * @return
     */
    @RequestMapping(value = "/mgrsite/withdrawLimit", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo withdrawLimit(String miniWithdrawETH, String miniWithdrawEOS, String miniWithdrawBGS){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "MINI_WITHDRAW_ETH", miniWithdrawETH);
        jedisClient.hset("operationCode", "MINI_WITHDRAW_EOS", miniWithdrawEOS);
        jedisClient.hset("operationCode", "MINI_WITHDRAW_BGS", miniWithdrawBGS);
        vo.setResult("修改提现最低限额成功");
        return vo;
    }

    /**
     * 功能设置 代理人价格
     * @return
     */
    @RequestMapping(value = "/mgrsite/priceBuyAgentBGS", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo withdrawLimit(String priceBuyAgentBGS){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "PRICE_BUYAGENT_BGS", priceBuyAgentBGS);
        vo.setResult("修改购买代理人价格成功");
        return vo;
    }

    /**
     * 功能设置 邀请好友设置
     * @return
     */
    @RequestMapping(value = "/mgrsite/inviteConf", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo inviteConf(String maxDailyInvitingCount, String profitInvitingBGS){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "MAX_DAILY_INVITING_COUNT", maxDailyInvitingCount);
        jedisClient.hset("operationCode", "PROFIT_INVITING_BGS", profitInvitingBGS);
        vo.setResult("修改邀请好友设置成功");
        return vo;
    }

    /**
     * 功能设置 dapp返现分配
     * @return
     */
    @RequestMapping(value = "/mgrsite/dappReturnConf", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo dappReturnConf(String profitDappLock, String profitDappAgent, String profitDappPlatform){
        JSONResultVo vo = new JSONResultVo();
        jedisClient.hset("operationCode", "PROFIT_DAPP_LOCK", profitDappLock);
        jedisClient.hset("operationCode", "PROFIT_DAPP_AGENT", profitDappAgent);
        jedisClient.hset("operationCode", "PROFIT_DAPP_PLATFORM", profitDappPlatform);
        vo.setResult("修改dapp返现分配比例成功");
        return vo;
    }
}
