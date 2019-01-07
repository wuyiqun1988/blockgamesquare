package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Banner;
import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IBannerService;
import com.jiazhuo.blockgamesquare.service.IGameListService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;

@Controller
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    /**
     * 首页轮播列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo bannerPage(@ModelAttribute("qo") QueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = bannerService.bannerPage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 首页轮播 增加或修改
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("首页轮播 增加或修改")
    public JSONResultVo saveOrUpdate(Banner banner){
        JSONResultVo vo = new JSONResultVo();
        try {
            bannerService.saveOrUpdate(banner);
            vo.setResult("更新或保存轮播广告成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 首页轮播 下架或上架
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("首页轮播 下架或上架")
    public JSONResultVo changeStatus(Long bid, Byte status){
        JSONResultVo vo = new JSONResultVo();
        try {
            bannerService.changeStatus(bid, status);
            if (status == 0){
                vo.setResult("下架成功");
            } else if (status == 1){
                vo.setResult("上架成功");
            } else {
                vo.setResult("请选择上架或下架");
            }
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 首页轮播 删除
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiredPermission("首页轮播 删除")
    public JSONResultVo deleteBanner(Long bid){
        JSONResultVo vo = new JSONResultVo();
        try {
            bannerService.deleteBanner(bid);
            vo.setResult("删除成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }
}
