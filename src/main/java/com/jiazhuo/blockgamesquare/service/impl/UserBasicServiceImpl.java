package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Inviter;
import com.jiazhuo.blockgamesquare.domain.UserBasic;
import com.jiazhuo.blockgamesquare.mapper.InviterMapper;
import com.jiazhuo.blockgamesquare.mapper.UserBasicMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.service.IUserBasicService;
import com.jiazhuo.blockgamesquare.vo.AgentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBasicServiceImpl implements IUserBasicService {
    @Autowired
    private UserBasicMapper userBasicMapper;
    @Autowired
    private InviterMapper inviterMapper;

    @Override
    public int queryRegisterUsers() {
        int count = userBasicMapper.queryRegisterUsers();
        return count;
    }

    @Override
    public PageResult usersPage(UserQueryObject qo) {
        int totalCount = userBasicMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = userBasicMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<UserBasic> exportUserData(String nickName, String phoneNumber) {
        List<UserBasic> userBasicList = userBasicMapper.exportUserData(nickName, phoneNumber);
        return userBasicList;
    }

    @Override
    public PageResult lowersPage(UserQueryObject qo, String inviterID) {
        int totalCount = userBasicMapper.queryLowerCount(qo, inviterID);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = userBasicMapper.queryLowerList(qo, inviterID);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public PageResult agentLowersPage(UserQueryObject qo, String agentUID) {
        int totalCount = userBasicMapper.queryAgentLowerCount(qo, agentUID);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = userBasicMapper.queryAgentLowerList(qo, agentUID);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public PageResult lowersActivePage(UserQueryObject qo, String BUID) {
        int totalCount = userBasicMapper.querylowersActiveCount(qo, BUID);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = userBasicMapper.queryLowerActiveList(qo, BUID);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public PageResult inviterPage(UserQueryObject qo) {
        int totalCount = inviterMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List<Inviter> data = inviterMapper.queryList(qo);
        for (Inviter in : data) {
            //设置下级人数
            int lowerAmount = inviterMapper.queryLowerAmount(in.getInviterID());
            in.setLowerAmount(lowerAmount);
        }
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<UserBasic> exportInviterData() {
        return inviterMapper.exportInviterData();
    }

    @Override
    public int queryLowerAmount(String UID) {
        return inviterMapper.queryLowerAmount(UID);
    }

    @Override
    public PageResult agentPage(QueryObject qo) {
        int totalCount = userBasicMapper.queryAgentCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List<UserBasic> data = userBasicMapper.queryAgentList(qo);
        List<AgentVo> vo = new ArrayList<>();
        //设置代理人相关信息
        for (UserBasic user : data) {
            AgentVo agent = new AgentVo();
            int lowerAmount = userBasicMapper.selectLowerAmount(user.getUID());
            agent.setNickName(user.getNickName());
            agent.setUID(user.getUID());
            agent.setRegisterTime(user.getRegisterTime());
            agent.setPhoneNumber(user.getPhoneNumber());
            agent.setLowerAmount(lowerAmount);
            //TODO 收益总额
            agent.setTotalIncome(0.0);
            vo.add(agent);
        }
        return new PageResult(vo, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<AgentVo> exportAgentData() {
        List<UserBasic> userBasicList = userBasicMapper.exportAgentData();
        List<AgentVo> vo = new ArrayList<>();
        //设置代理人相关信息
        for (UserBasic user : userBasicList) {
            AgentVo agent = new AgentVo();
            int lowerAmount = userBasicMapper.selectLowerAmount(user.getUID());
            agent.setNickName(user.getNickName());
            agent.setUID(user.getUID());
            agent.setRegisterTime(user.getRegisterTime());
            agent.setPhoneNumber(user.getPhoneNumber());
            agent.setLowerAmount(lowerAmount);
            //TODO 收益总额
            agent.setTotalIncome(0.0);
            vo.add(agent);
        }
        return vo;
    }

    @Override
    public UserBasic userinfo(String UID) {
        return userBasicMapper.userinfo(UID);
    }
}
