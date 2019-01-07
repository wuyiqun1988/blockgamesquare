package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.mapper.InviterMapper;
import com.jiazhuo.blockgamesquare.service.IInviterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InviterServiceImpl implements IInviterService {
    @Autowired
    private InviterMapper inviterMapper;
}
