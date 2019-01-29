package com.jiazhuo.blockgamesquare.controller;

import com.gexin.fastjson.JSONObject;
import com.jiazhuo.blockgamesquare.domain.UserBasic;
import com.jiazhuo.blockgamesquare.domain.UserPrivate;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.service.IUserBasicService;
import com.jiazhuo.blockgamesquare.service.IUserPrivateService;
import com.jiazhuo.blockgamesquare.util.*;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserbasicController {
    @Autowired
    private IUserBasicService userbasicService;
    @Autowired
    private IUserPrivateService userPrivateService;


    /**
     * 查询有多少注册用户
     * @return
     */
    @RequestMapping(value = "/mgrsite/info/registerUsers", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo totalAmount(){
        JSONResultVo vo = new JSONResultVo();
        int count = userbasicService.queryRegisterUsers();
        vo.setResult(count);
        return vo;
    }

    /**
     * 用户列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/users", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo usersPage(@ModelAttribute("qo") UserQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = userbasicService.usersPage(qo);
        vo.setResult(result);
        return vo;
    }


    /**
     * 查看用户实名认证信息
     * @param UID
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/realAuth", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("查看用户实名认证信息")
    public JSONResultVo realAuthInfo(String UID){
        if (StringUtil.isNull(UID)){
            return JSONResultVo.error("UID不能为空");
        }
        JSONResultVo vo = new JSONResultVo();
        UserPrivate result = userPrivateService.realAuthInfo(UID);
        if (result == null){
            return JSONResultVo.error("用户未实名认证");
        }
        vo.setResult(result);
        return vo;
    }


    /**
     * excel导出用户列表
     * @param nickName
     * @param phoneNumber
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/users/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出用户列表")
    public void exportUserData(String nickName, String phoneNumber, HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=userInfo.xls");
        //查询需要的用户信息
        List<UserBasic> userBasicList = userbasicService.exportUserData(nickName, phoneNumber);
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("用户名");
        hander.createCell(1).setCellValue("手机号");
        hander.createCell(2).setCellValue("性别");
        hander.createCell(3).setCellValue("是否代理人");
        hander.createCell(4).setCellValue("注册时间");
        for (int i = 0; i < userBasicList.size(); i++){
            UserBasic userBasic = userBasicList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            currentRow.createCell(0).setCellValue(userBasic.getNickName());
            currentRow.createCell(1).setCellValue(userBasic.getPhoneNumber());
            switch (userBasic.getSex()){
                case 0:
                    currentRow.createCell(2).setCellValue("女");
                    break;
                case 1:
                    currentRow.createCell(2).setCellValue("男");
                    break;
            }
            switch (userBasic.getIsAgency()){
                case 0:
                    currentRow.createCell(3).setCellValue("否");
                    break;
                case 1:
                    currentRow.createCell(3).setCellValue("是");
                    break;
            }
            String registerTime = DateUtil.dateToStrLong(userBasic.getRegisterTime());
            currentRow.createCell(4).setCellValue(registerTime);
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }

    /**
     * 邀请好友列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/inviters", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo inviterPage(@ModelAttribute("qo") UserQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = userbasicService.inviterPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 邀请好友下级列表
     * @param qo
     * @param inviterID
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/lowers", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo lowersPage(@ModelAttribute("qo") UserQueryObject qo, String inviterID){
        if (StringUtil.isNull(inviterID)){
            return JSONResultVo.error("inviterID不能为空");
        }
        JSONResultVo vo = new JSONResultVo();
        PageResult result = userbasicService.lowersPage(qo, inviterID);
        vo.setResult(result);
        return vo;
    }

    /**
     * excel导出邀请好友列表
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/users/inviters/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出邀请好友列表")
    public void exportInviterData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=inviterInfo.xls");
        //查询需要的用户信息
        List<UserBasic> userBasicList = userbasicService.exportInviterData();
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
        for (int i = 0; i < userBasicList.size(); i++){
            UserBasic userBasic = userBasicList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            currentRow.createCell(0).setCellValue(userBasic.getNickName());
            currentRow.createCell(1).setCellValue(userBasic.getPhoneNumber());
            int lowerAmount = userbasicService.queryLowerAmount(userBasic.getUID());
            currentRow.createCell(2).setCellValue(lowerAmount);
            String registerTime = DateUtil.dateToStrLong(userBasic.getRegisterTime());
            currentRow.createCell(3).setCellValue(registerTime);
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }

    /**
     * 预邀请(注册有礼)
     * @param phoneNum
     * @param invitedCode
     * @return
     */
    @RequestMapping(value = "/appsite/preInviter", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo preInviter(String phoneNum, String invitedCode, ServletResponse res){
        AccessUtil.access(res); //跨域请求
        if (StringUtil.isNull(phoneNum)){
            return JSONResultVo.error("phoneNum不能为空");
        }
        if (StringUtil.isNull(invitedCode)){
            return JSONResultVo.error("invitedCode不能为空");
        }
        JSONResultVo vo = new JSONResultVo();
        //请求服务器预邀请接口
        String url = HttpClientUtil.HOST_POST + HttpClientUtil.PREINVITER;
        Map<String, String> map = new HashMap<>();
        map.put("phoneNum", phoneNum);
        map.put("invitedCode", invitedCode);
        String result = HttpClientUtil.doPost(url, map);
        JSONObject json = JSONObject.parseObject(result);
        vo.setResult(json);
        return vo;
    }
}
