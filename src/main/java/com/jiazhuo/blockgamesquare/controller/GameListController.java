package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.GameList;
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
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/games", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo gamePage(@ModelAttribute("qo") GameQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
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
     * 设置游戏排序序号
     * @param gid
     * @param no
     * @return
     */
    @RequestMapping(value = "/mgrsite/games/setSort", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("设置游戏排序序号")
    public JSONResultVo setSort(Long gid, Byte no){
        gameListService.setSort(gid, no);
        return JSONResultVo.ok("设置排序成功");
    }
}
