package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.WithdrawMoney;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.WithdrawMoneyQueryObject;
import com.jiazhuo.blockgamesquare.service.IWithdrawMoneyService;
import com.jiazhuo.blockgamesquare.util.DateUtil;
import com.jiazhuo.blockgamesquare.util.HttpClientUtil;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.StringUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WithdrawMoneyController {
    @Autowired
    private IWithdrawMoneyService withdrawMoneyService;

    /**
     * 提现申请列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/withdrawMoney/apply", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo withdrawMoneyApplyPage(@ModelAttribute("qo") WithdrawMoneyQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = withdrawMoneyService.withdrawMoneyApplyPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 提现审核
     * @return
     */
    @RequestMapping(value = "/mgrsite/withdrawMoney/audit", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("提现审核")
    public JSONResultVo withdrawMoneyAudit(String UID, String WID, Integer tokenType, Double tokenAmount, Double gasPrice, String auditor, String remark, Integer status){
        if (StringUtil.isNull(UID)){
            return JSONResultVo.error("UID不能为空");
        }
        if (StringUtil.isNull(WID)){
            return JSONResultVo.error("WID不能为空");
        }
        if (StringUtil.isNull(tokenType)){
            return JSONResultVo.error("tokenType不能为空");
        }
        if (StringUtil.isNull(tokenAmount)){
            return JSONResultVo.error("tokenAmount不能为空");
        }
        if (StringUtil.isNull(gasPrice)){
            return JSONResultVo.error("gasPrice不能为空");
        }
        if (StringUtil.isNull(auditor)){
            return JSONResultVo.error("auditor不能为空");
        }
        if (StringUtil.isNull(remark)){
            return JSONResultVo.error("remark不能为空");
        }
        if (StringUtil.isNull(status)){
            return JSONResultVo.error("status不能为空");
        }
        JSONResultVo vo = new JSONResultVo();
        //请求服务器提现审核接口
        String url = HttpClientUtil.HOST_POST + HttpClientUtil.WITHDRAW_AUDIT;
        Map<String, String> map = new HashMap<>();
        map.put("UID", UID);
        map.put("WID", WID);
        map.put("tokenType", String.valueOf(tokenType));
        map.put("tokenAmount", String.valueOf(tokenAmount));
        map.put("gasPrice", String.valueOf(gasPrice));
        map.put("auditor", auditor);
        map.put("remark", remark);
        map.put("status", String.valueOf(status));
        String result = HttpClientUtil.doPost(url, map);
        vo.setResult(result);
        //记录系统日志
        withdrawMoneyService.recordWithdrawMoneyAuditSystemLog();
        return vo;
    }


    /**
     * 提现审核成功列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/withdrawMoney/pass", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo withdrawMoneyPassPage(@ModelAttribute("qo") WithdrawMoneyQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = withdrawMoneyService.withdrawMoneyPassPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 提现审核失败列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/withdrawMoney/fail", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo withdrawMoneyFailPage(@ModelAttribute("qo") WithdrawMoneyQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = withdrawMoneyService.withdrawMoneyFailPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * excel导出提现审核通过列表
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/withdrawMoney/pass/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出提现审核通过列表")
    public void exportWithdrawMoneyPassData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=withdrawMoneyPassInfo.xls");
        //查询需要的用户信息
        List<WithdrawMoney> withdrawMoneyList = withdrawMoneyService.exportWithdrawMoneyPassData();
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("申请时间");
        hander.createCell(1).setCellValue("用户名");
        hander.createCell(2).setCellValue("手机号");
        hander.createCell(3).setCellValue("数量");
        hander.createCell(4).setCellValue("货币类型");
        hander.createCell(5).setCellValue("审核人");
        hander.createCell(6).setCellValue("审核时间");
        hander.createCell(7).setCellValue("审核备注");
        for (int i = 0; i < withdrawMoneyList.size(); i++){
            WithdrawMoney withdrawMoney = withdrawMoneyList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            String createdTime = DateUtil.dateToStrLong(withdrawMoney.getCreatedTime()); //转化成string
            currentRow.createCell(0).setCellValue(createdTime);
            currentRow.createCell(1).setCellValue(withdrawMoney.getNickName());
            currentRow.createCell(2).setCellValue(withdrawMoney.getPhoneNumber());
            currentRow.createCell(3).setCellValue(withdrawMoney.getAmount());
            switch (withdrawMoney.getTokenType()){
                case 1:
                    currentRow.createCell(4).setCellValue("ETH");
                    break;
                case 2:
                    currentRow.createCell(4).setCellValue("EOS");
                    break;
                case 3:
                    currentRow.createCell(4).setCellValue("BGS");
                    break;
            }
            currentRow.createCell(5).setCellValue(withdrawMoney.getAuditor());
            String auditTime = DateUtil.dateToStrLong(withdrawMoney.getAuditTime()); //转化成string
            currentRow.createCell(6).setCellValue(auditTime);
            currentRow.createCell(7).setCellValue(withdrawMoney.getRemark());
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }

    /**
     * excel导出提现审核失败列表
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/withdrawMoney/fail/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出提现审核失败列表")
    public void exportWithdrawMoneyFailData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=withdrawMoneyFailInfo.xls");
        //查询需要的用户信息
        List<WithdrawMoney> withdrawMoneyList = withdrawMoneyService.exportWithdrawMoneyFailData();
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("申请时间");
        hander.createCell(1).setCellValue("用户名");
        hander.createCell(2).setCellValue("手机号");
        hander.createCell(3).setCellValue("数量");
        hander.createCell(4).setCellValue("货币类型");
        hander.createCell(5).setCellValue("审核人");
        hander.createCell(6).setCellValue("审核时间");
        hander.createCell(7).setCellValue("审核备注");
        for (int i = 0; i < withdrawMoneyList.size(); i++){
            WithdrawMoney withdrawMoney = withdrawMoneyList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            String createdTime = DateUtil.dateToStrLong(withdrawMoney.getCreatedTime()); //转化成string
            currentRow.createCell(0).setCellValue(createdTime);
            currentRow.createCell(1).setCellValue(withdrawMoney.getNickName());
            currentRow.createCell(2).setCellValue(withdrawMoney.getPhoneNumber());
            currentRow.createCell(3).setCellValue(withdrawMoney.getAmount());
            switch (withdrawMoney.getTokenType()){
                case 1:
                    currentRow.createCell(4).setCellValue("ETH");
                    break;
                case 2:
                    currentRow.createCell(4).setCellValue("EOS");
                    break;
                case 3:
                    currentRow.createCell(4).setCellValue("BGS");
                    break;
            }
            currentRow.createCell(5).setCellValue(withdrawMoney.getAuditor());
            String auditTime = DateUtil.dateToStrLong(withdrawMoney.getAuditTime()); //转化成string
            currentRow.createCell(6).setCellValue(auditTime);
            currentRow.createCell(7).setCellValue(withdrawMoney.getRemark());
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }
}
