package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Banner;
import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IBannerService;
import com.jiazhuo.blockgamesquare.service.IGameListService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.UploadUtil;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
public class BannerController {

    @Autowired
    private IBannerService bannerService;
    @Autowired
    private ServletContext ctx;

    /**
     * 首页轮播列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo bannerPage(@ModelAttribute("qo") QueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = bannerService.bannerPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 首页轮播 增加或修改
     * @param banner
     * @param pic
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("首页轮播 增加或修改")
    public JSONResultVo saveOrUpdate(Banner banner, @RequestParam("pic") MultipartFile pic){
        //处理图片
        if (pic != null && pic.getContentType().startsWith("image") && pic.getSize() > 0){

            //是否需要删除旧的图片
            if (StringUtils.hasLength(banner.getPhoto())){
                UploadUtil.deleteFile(ctx, banner.getPhoto());
            }

            //符合上传要求的图片
            String path = UploadUtil.upload(pic, ctx.getRealPath("/upload"));
            banner.setPhoto(path);
        }
        bannerService.saveOrUpdate(banner);
        return JSONResultVo.ok("更新或保存轮播广告成功");
    }

    /**
     * 首页轮播 下架或上架
     * @param bid
     * @param status
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("首页轮播 下架或上架")
    public JSONResultVo changeStatus(Long bid, Byte status){
        JSONResultVo vo = new JSONResultVo();
        bannerService.changeStatus(bid, status);
        if (status == 0){
            vo.setResult("下架成功");
        } else if (status == 1){
            vo.setResult("上架成功");
        } else {
            vo.setResult("请选择上架或下架");
        }
        return vo;
    }

    /**
     * 首页轮播 删除
     * @param bid
     * @return
     */
    @RequestMapping(value = "/mgrsite/banner/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiredPermission("首页轮播 删除")
    public JSONResultVo deleteBanner(Long bid){
        bannerService.deleteBanner(bid);
        return JSONResultVo.ok("删除成功");
    }
}
