package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BgUserMapper {
    int deleteByPrimaryKey(Long bid);

    int insert(BgUser record);

    BgUser selectByPrimaryKey(Long bid);


    int queryCount(QueryObject qo);

    List<BgUser> queryList(QueryObject qo);

    void deleteRoleRelation(Long bid);

    void insertRoleRelation(@Param("bid") Long bid, @Param("rid") Long rid);

    BgUser selectBgUserByInfo(@Param("username") String username, @Param("password") String password);

    void updateUserInfo(@Param("username") String username, @Param("bid") Long bid);

    /**
     * 判断旧密码是否正确
     * @param bid
     * @param oldPwd
     * @return
     */
    int checkOldPwd(@Param("bid") Long bid, @Param("oldPwd") String oldPwd);

    /**
     * 修改密码
     * @param bid
     * @param newPwd
     */
    void changePwd(@Param("bid") Long bid, @Param("newPwd") String newPwd);

    /**
     * 重置密码
     * @param bid
     */
    void resetPwd(@Param("bid") Long bid, @Param("initpassword") String initpassword);

    /**
     * 查看用户名是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);


    void changeStatusOrAdmin(@Param("bid") Long bid, @Param("state") int state, @Param("admin") boolean admin);
}