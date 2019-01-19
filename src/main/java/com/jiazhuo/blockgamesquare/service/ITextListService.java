package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.TextList;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface ITextListService {
    List<TextList> queryList();
    @SystemlogAnnotation("修改文本")
    void update(TextList textList);
    @SystemlogAnnotation("新增文本")
    void save(TextList textList);
    @SystemlogAnnotation("删除文本")
    void delete(Long tid);

    /**
     * 查询文本信息
     * @param title
     * @param type
     * @return
     */
    TextList queryText(String title, Byte type);

    TextList get(Long tid);
}
