package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.TextList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TextListMapper {
    int deleteByPrimaryKey(Long tid);

    int insert(TextList record);

    TextList selectByPrimaryKey(Long tid);

    List<TextList> selectAll();

    int updateByPrimaryKey(TextList record);

    TextList queryText(@Param("title") String title, @Param("type") Byte type);
}