package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.EOSTOKEN;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EOSTOKENMapper {
    int deleteByPrimaryKey(@Param("UID") String UID, @Param("type") Integer type);

    int insert(EOSTOKEN record);

    EOSTOKEN selectByPrimaryKey(@Param("UID") String UID, @Param("type") Integer type);

    List<EOSTOKEN> selectAll();

    int updateByPrimaryKey(EOSTOKEN record);
}