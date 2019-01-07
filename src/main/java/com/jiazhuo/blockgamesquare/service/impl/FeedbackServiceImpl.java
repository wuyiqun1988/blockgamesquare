package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Feedback;
import com.jiazhuo.blockgamesquare.mapper.FeedbackMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public PageResult feedbackPage(QueryObject qo) {
        int totalCount = feedbackMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = feedbackMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void userFeedback(String uid, String content, String contact) {
        Feedback feedback = new Feedback();
        feedback.setUID(uid);
        feedback.setContent(content);
        feedback.setContact(contact);
        feedback.setCreateTime(new Date());
        feedbackMapper.insert(feedback);
    }
}
