package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Banner;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

public interface IBannerService {
    PageResult bannerPage(QueryObject qo);
    @SystemlogAnnotation("首页轮播增加或保存")
    void saveOrUpdate(Banner banner);
    @SystemlogAnnotation("首页轮播上架或下架")
    void changeStatus(Long bid, Byte status);
    @SystemlogAnnotation("首页轮播 删除")
    void deleteBanner(Long bid);
}
