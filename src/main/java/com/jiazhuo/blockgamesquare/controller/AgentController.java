package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.qo.AgentQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.service.IUserBasicService;
import com.jiazhuo.blockgamesquare.util.DateUtil;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.StringUtil;
import com.jiazhuo.blockgamesquare.vo.AgentVo;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AgentController {
    @Autowired
    private IUserBasicService userBasicService;

    /**
     * 代理人列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/agents", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo agentPage(@ModelAttribute("qo") AgentQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = userBasicService.agentPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 代理人下级列表
     * @param qo
     * @param agentUID
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/agentlowers", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo agentLowersPage(@ModelAttribute("qo") UserQueryObject qo, String agentUID){
        if (StringUtil.isNull(agentUID)){
            return JSONResultVo.error("agentUID不能为空");
        }
        JSONResultVo vo = new JSONResultVo();
        PageResult result = userBasicService.agentLowersPage(qo, agentUID);
        vo.setResult(result);
        return vo;
    }

    /**
     * excel导出代理人列表
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/users/agents/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出代理人列表")
    public void exportInviterData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=agentInfo.xls");
        //查询需要的用户信息
        List<AgentVo> agentVoList = userBasicService.exportAgentData();
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("用户名");
        hander.createCell(1).setCellValue("手机号");
        hander.createCell(2).setCellValue("下级人数");
        hander.createCell(3).setCellValue("注册时间");
        hander.createCell(4).setCellValue("收益总额");

        for (int i = 0; i < agentVoList.size(); i++){
            AgentVo agentVo = agentVoList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            currentRow.createCell(0).setCellValue(agentVo.getNickName());
            currentRow.createCell(1).setCellValue(agentVo.getPhoneNumber());
            currentRow.createCell(2).setCellValue(agentVo.getLowerAmount());
            String registerTime = DateUtil.dateToStrLong(agentVo.getRegisterTime());
            currentRow.createCell(3).setCellValue(registerTime);
            currentRow.createCell(4).setCellValue(agentVo.getTotalIncome());

        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }

}
