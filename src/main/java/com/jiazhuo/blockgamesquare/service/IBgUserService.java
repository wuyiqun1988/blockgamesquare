package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

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
    void login(String username, String password);

    /**
     * 修改用户个人信息
     * @param current
     * @return
     */
    @SystemlogAnnotation("修改用户个人信息(用户修改)")
    void userUpdate(String username, Long bid);

    /**
     * 修改密码
     * @param bid
     * @param oldPwd
     * @param newPwd
     */
    @SystemlogAnnotation("修改后台用户密码(用户修改)")
    void changePwd(Long bid, String oldPwd, String newPwd);

    /**
     * 重置密码
     * @param bid
     */
    @SystemlogAnnotation("重置密码(管理员来重置)")
    void resetPwd(Long bid);

    /**
     * 管理员新建后台用户
     * @param bgUser
     */
    @SystemlogAnnotation("管理员新建后台用户")
    void newBgUser(BgUser bgUser);

    /**
     * 查看用户名是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 修改用户状态或分配管理员权限(管理员修改)
     * @param status
     * @param admin
     */
    @SystemlogAnnotation("修改用户状态或分配管理员权限(管理员修改)")
    void changeStatusOrAdmin(Long bid, int state, boolean admin);
}
