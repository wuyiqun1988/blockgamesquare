package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.qo.GameQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.service.IGameListService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import com.jiazhuo.blockgamesquare.util.UploadUtil;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

@Controller
public class GameListController {

    @Autowired
    private IGameListService gameListService;
    @Autowired
    private ServletContext ctx;

    /**
     * 游戏列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/games", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo gamePage(@ModelAttribute("qo") GameQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        if (qo.getType() == null){
            JSONResultVo.error("请选择游戏类型");
        }
        PageResult result = gameListService.gamePage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 游戏列表app端
     * @param qo
     * @return
     */
    @RequestMapping(value = "/appsite/games", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult appGamePage(@ModelAttribute("qo") GameQueryObject qo){
        //返回上架的游戏
        qo.setStatus(GameList.GAME_ON);
        PageResult result = gameListService.gamePage(qo);
        SuperResult res = SuperResult.ok(result);
        res.setMsg("请求游戏列表成功");
        return res;
    }


    /**
     * 游戏新增或修改
     * @param gameList
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("游戏新增或修改")
    public JSONResultVo saveOrUpdate(GameList gameList){
        gameListService.saveOrUpdate(gameList);
        return JSONResultVo.ok("更新或保存成功");
    }


    /**
     * 游戏上架或下架
     * @param gid
     * @param status
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("游戏上架或下架")
    public JSONResultVo changeStatus(Long gid, Byte status){
        JSONResultVo vo = new JSONResultVo();
        gameListService.changeStatus(gid, status);
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
     * 游戏置顶排序
     * @param gid1
     * @param gid2
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/updateSort", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("游戏置顶排序")
    public JSONResultVo updateSort(Long gid1, Long gid2){
        gameListService.updateSort(gid1, gid2);
        return JSONResultVo.ok("更改排序成功");
    }

    /**
     * 上传图片
     * @param pic
     * @return
     */
    @RequestMapping(value = "/mgrsite/fireUpload", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo fireUpload(String pic, String suffix){
        JSONResultVo vo = new JSONResultVo();
        String path = GenerateImage(pic, suffix);
        vo.setResult(path);
        return vo;
    }

    //base64字符串转化成图片
    private String GenerateImage(String imgStr, String suffix) {
        if (suffix == null){
            suffix = "png"; //默认png格式
        }
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return "";
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String path = "upload/" + UUID.randomUUID().toString() +"." + suffix;
            String imgFilePath =  ctx.getRealPath("/") + path;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return path;
        }
        catch (Exception e)
        {
            return "";
        }
    }

}
