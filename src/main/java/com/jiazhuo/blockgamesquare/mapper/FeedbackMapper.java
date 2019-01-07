package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Feedback;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import java.util.List;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Long fid);

    int insert(Feedback record);

    Feedback selectByPrimaryKey(Long fid);

    List<Feedback> selectAll();

    int updateByPrimaryKey(Feedback record);

    int queryCount(QueryObject qo);

    List<Feedback> queryList(QueryObject qo);
}