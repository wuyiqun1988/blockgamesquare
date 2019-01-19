package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.TextList;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.service.ITextListService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.StringUtil;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class TextListController {
    @Autowired
    private ITextListService textListService;

    /**
     * 文本列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/textList", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo textList(){
        JSONResultVo vo = new JSONResultVo();
        List<TextList> data = textListService.queryList();
        vo.setResult(data);
        return vo;
    }

    /**
     * 修改或新增文本
     * @param textList
     * @return
     */
    @RequestMapping(value = "/mgrsite/text/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("修改或新增文本")
    public JSONResultVo saveOrUpdate(TextList textList){
        if (textList.getTid() != null) {
            textListService.update(textList);
            return JSONResultVo.ok("修改成功");
        } else {
            textListService.save(textList);
            return JSONResultVo.ok("新增成功");
        }
    }

    /**
     * 删除文本
     * @param tid
     * @return
     */
    @RequestMapping(value = "/mgrsite/text/delete", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("删除文本")
    public JSONResultVo delete(Long tid){
        if (tid != null) {
            TextList textList = textListService.get(tid);
            if (textList.getType() == 1){
                return JSONResultVo.error("无法删除");
            }
            textListService.delete(tid);
            return JSONResultVo.ok("删除成功");
        } else {
            return JSONResultVo.error("请选择需要删除的文本");
        }
    }

    /**
     * 获取文本信息(app端)
     * @return
     */
    @RequestMapping(value = "/appsite/textList", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo appText(ServletResponse res){
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        JSONResultVo vo = new JSONResultVo();
        List<TextList> data = textListService.queryList();
        if (data == null){
            return JSONResultVo.error("无数据");
        }
        vo.setResult(data);
        return vo;
    }
}
