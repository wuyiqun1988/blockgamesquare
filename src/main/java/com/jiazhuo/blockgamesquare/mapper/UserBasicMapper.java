package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.UserBasic;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserBasicMapper {
    int deleteByPrimaryKey(String UID);

    int queryRegisterUsers();

    int queryCount(QueryObject qo);

    List<UserBasic> queryList(QueryObject qo);

    /**
     * excel导出用户列表
     *
     * @param nickName
     * @param phoneNumber
     * @return
     */
    List<UserBasic> exportUserData(@Param("nickName") String nickName, @Param("phoneNumber") String phoneNumber);

    int queryLowerCount(@Param("qo") UserQueryObject qo, @Param("BUID") String BUID);

    List<UserBasic> queryLowerList(@Param("qo") UserQueryObject qo, @Param("BUID") String BUID);


    int querylowersActiveCount(@Param("qo") UserQueryObject qo, @Param("BUID") String BUID);

    List<UserBasic> queryLowerActiveList(@Param("qo") UserQueryObject qo, @Param("BUID") String BUID);

    int queryLowerAmount(String UID);

    int queryAgentCount(QueryObject qo);

    List<UserBasic> queryAgentList(QueryObject qo);

    /**
     * 查询下级人数
     * @param uid
     * @return
     */
    int selectLowerAmount(String uid);

    /**
     * excel 导出代理人列表
     * @return
     */
    List<UserBasic> exportAgentData();
}