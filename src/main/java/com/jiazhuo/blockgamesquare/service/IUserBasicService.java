package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.UserBasic;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;
import com.jiazhuo.blockgamesquare.vo.AgentVo;

import java.util.List;

public interface IUserBasicService {
    int queryRegisterUsers();

    /**
     * 用户列表
     * @param qo
     * @return
     */
    PageResult usersPage(UserQueryObject qo);

    /**
     * excel导出用户列表
     *
     * @param nickName
     * @param keyword
     * @return
     */
    @SystemlogAnnotation("excel导出用户列表")
    List<UserBasic> exportUserData(String nickName, String phoneNumber);

    /**
     * 查看邀请好友(代理人)下级列表
     * @param qo
     * @param uid
     * @return
     */
    PageResult lowersPage(UserQueryObject qo, String BUID);

    /**
     * 代理人每日活跃下线列表
     * @param qo
     * @return
     */
    PageResult lowersActivePage(UserQueryObject qo, String BUID);

    /**
     * 邀请好友列表
     * @param qo
     * @return
     */
    PageResult inviterPage(UserQueryObject qo);

    /**
     * excel导出邀请人列表
     * @return
     */
    @SystemlogAnnotation("excel导出邀请人列表")
    List<UserBasic> exportInviterData();

    /**
     * 查询下级人数
     * @param uid
     * @return
     */
    int queryLowerAmount(String UID);

    /**
     * 代理人列表
     * @param qo
     * @return
     */
    PageResult agentPage(QueryObject qo);

    /**
     * exce导出代理人列表
     * @return
     */
    @SystemlogAnnotation("exce导出代理人列表")
    List<AgentVo> exportAgentData();
}
