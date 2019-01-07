package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Banner;
import com.jiazhuo.blockgamesquare.mapper.BannerMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public PageResult bannerPage(QueryObject qo) {
        int totalCount = bannerMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data =bannerMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(Banner banner) {
        if (banner.getBid() != null){
            bannerMapper.updateByPrimaryKey(banner);
        } else {
            bannerMapper.insert(banner);
        }
    }

    @Override
    public void changeStatus(Long bid, Byte status) {
        bannerMapper.changeStatus(bid, status);
    }

    @Override
    public void deleteBanner(Long bid) {
        bannerMapper.deleteByPrimaryKey(bid);
    }
}
