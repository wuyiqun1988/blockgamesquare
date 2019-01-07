package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Inviter;
import java.util.List;

import com.jiazhuo.blockgamesquare.domain.UserBasic;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import org.apache.ibatis.annotations.Param;

public interface InviterMapper {
    int deleteByPrimaryKey(@Param("inviterID") String inviterID, @Param("beinvitedID") String beinvitedID);

    int insert(Inviter record);

    Inviter selectByPrimaryKey(@Param("inviterID") String inviterID, @Param("beinvitedID") String beinvitedID);

    List<Inviter> selectAll();

    int updateByPrimaryKey(Inviter record);

    int queryCount(UserQueryObject qo);

    List<Inviter> queryList(UserQueryObject qo);

    /**
     * excel导出邀请人列表
     * @return
     */
    List<UserBasic> exportInviterData();

    /**
     * 查询下级人数
     * @param uid
     * @return
     */
    int queryLowerAmount(String uid);
}