package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.ETHTOKEN;
import java.util.List;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import org.apache.ibatis.annotations.Param;

public interface ETHTOKENMapper {

    int insert(ETHTOKEN record);

    ETHTOKEN selectByPrimaryKey(@Param("UID") String UID, @Param("type") Integer type);

    List<ETHTOKEN> selectAll();

    int updateByPrimaryKey(ETHTOKEN record);

}