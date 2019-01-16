package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.TextList;
import com.jiazhuo.blockgamesquare.mapper.TextListMapper;
import com.jiazhuo.blockgamesquare.service.ITextListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextListServiceImpl implements ITextListService {
    @Autowired
    private TextListMapper textListMapper;

    @Override
    public List<TextList> queryList() {
        List<TextList> textLists = textListMapper.selectAll();
        return textLists;
    }

    @Override
    public void update(TextList textList) {
        textListMapper.updateByPrimaryKey(textList);
    }

    @Override
    public void save(TextList textList) {
        textListMapper.insert(textList);
    }

    @Override
    public void delete(Long tid) {
        textListMapper.deleteByPrimaryKey(tid);
    }

    @Override
    public TextList queryText(String title, Byte type) {
        return textListMapper.queryText(title, type);
    }
}
