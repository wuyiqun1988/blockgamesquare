package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.GameList;
import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.qo.GameQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.service.IGameListService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameListController {

    @Autowired
    private IGameListService gameListService;

    /**
     * 游戏列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/games", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo gamePage(@ModelAttribute("qo") GameQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = gameListService.gamePage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 游戏列表app端
     * @return
     */
    @RequestMapping(value = "/appsite/games", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult appGamePage(@ModelAttribute("qo") GameQueryObject qo){
        PageResult result = gameListService.gamePage(qo);
        SuperResult res = SuperResult.ok(result);
        res.setMsg("请求游戏列表成功");
        return res;
    }




    /**
     * 游戏新增或修改
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("游戏新增或修改")
    public JSONResultVo saveOrUpdate(GameList gameList){
        JSONResultVo vo = new JSONResultVo();
        try {
            gameListService.saveOrUpdate(gameList);
            vo.setResult("更新或保存成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 游戏上架或下架
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("游戏上架或下架")
    public JSONResultVo changeStatus(Long gid, Byte status){
        JSONResultVo vo = new JSONResultVo();
        try {
            gameListService.changeStatus(gid, status);
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
     * 游戏置顶排序
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/updateSort", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("游戏置顶排序")
    public JSONResultVo updateSort(Long gid1, Long gid2){
        JSONResultVo vo = new JSONResultVo();
        try {
            gameListService.updateSort(gid1, gid2);
            vo.setResult("更改排序成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 设置游戏排序序号
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/setSort", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("设置游戏排序序号")
    public JSONResultVo setSort(Long gid, Byte no){
        JSONResultVo vo = new JSONResultVo();
        try {
            gameListService.setSort(gid, no);
            vo.setResult("设置排序成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }
}
