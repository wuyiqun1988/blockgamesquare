package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Menu;
import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IMenuService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 菜单列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/menuPage", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo menuPage(@ModelAttribute("qo") QueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = menuService.menuPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 保存或更新菜单
     * @return
     */
    @RequestMapping(value = "/mgrsite/menu/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("保存或更新菜单")
    public JSONResultVo saveOrUpdate(Menu menu){
        JSONResultVo vo = new JSONResultVo();
        if (menu.getMid() == null){
            menuService.save(menu);
        } else {
            menuService.update(menu);
        }
        vo.setResult("保存或更新成功");
        return vo;
    }

    /**
     * 查询根菜单
     * @return
     */
    @RequestMapping(value = "/mgrsite/menu/rootMenus", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("查询根菜单")
    public JSONResultVo selectRootMenus(){
        JSONResultVo vo = new JSONResultVo();
        List<Menu> rootMenus = menuService.selectRootMenus();
        vo.setResult(rootMenus);
        return vo;
    }

    /**
     * 查询子菜单
     * @return
     */
    @RequestMapping(value = "/mgrsite/menu/childrenMenus", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("查询子菜单")
    public JSONResultVo selectChildrenMenus(Long mid){
        JSONResultVo vo = new JSONResultVo();
        List<Menu> childrenMenus = menuService.selectChildrenMenus(mid);
        vo.setResult(childrenMenus);
        return vo;
    }

    /**
     * 查询当前用户拥有的菜单
     * @return
     */
    @RequestMapping(value = "/mgrsite/menu/bgUserMenus", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo selectBgUserMenus(){
        JSONResultVo vo = new JSONResultVo();
        List<Menu> bgUserMenus = menuService.selectBgUserMenus();
        vo.setResult(bgUserMenus);
        return vo;
    }

}
