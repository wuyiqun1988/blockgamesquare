package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;

public interface IFeedbackService {
    PageResult feedbackPage(QueryObject qo);

    /**
     * 用户进行反馈
     * @param uid
     * @param content
     * @param contact
     */
    void userFeedback(String uid, String content, String contact);
}
