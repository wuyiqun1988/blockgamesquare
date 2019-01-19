package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;

import java.util.List;

public interface IBgUserService {
    @SystemlogAnnotation("管理员分配用户角色")
    void saveBgUserRole(Long  bid, Long[] rids);
    void delete(Long bid);
    BgUser get(Long bid);

    PageResult bgUserPage(QueryObject qo);

    /**
     * 登录的业务方法
     * @param username
     * @param password
     */
    JSONResultVo login(String username, String password);

    /**
     * 修改用户个人信息
     * @param username
     * @param bid
     * @return
     */
    @SystemlogAnnotation("修改用户个人信息(用户修改)")
    boolean userUpdate(String username, Long bid);

    /**
     * 修改密码
     * @param bid
     * @param oldPwd
     * @param newPwd
     */
    @SystemlogAnnotation("修改后台用户密码(用户修改)")
    boolean changePwd(Long bid, String oldPwd, String newPwd);

    /**
     * 重置密码
     * @param bid
     */
    @SystemlogAnnotation("重置密码(管理员来重置)")
    boolean resetPwd(Long bid);

    /**
     * 管理员新建后台用户
     * @param bgUser
     */
    @SystemlogAnnotation("管理员新建后台用户")
    boolean newBgUser(BgUser bgUser, Long rid);

    /**
     * 查看用户名是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 修改用户状态(管理员修改)
     * @param bid
     * @param state
     * @param admin
     * @return
     */
    @SystemlogAnnotation("修改用户状态(管理员修改)")
    boolean changeStatus(Long bid, int state);

    /**
     * 初始化第一个管理员
     */
    void initAdmin();

    List<BgUser> queryAll();
}
