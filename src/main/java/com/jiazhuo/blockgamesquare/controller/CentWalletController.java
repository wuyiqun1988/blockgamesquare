package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Transfer;
import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.qo.WalletItemQueryObject;
import com.jiazhuo.blockgamesquare.service.ICentWalletService;
import com.jiazhuo.blockgamesquare.service.ITransferService;
import com.jiazhuo.blockgamesquare.util.DateUtil;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import com.jiazhuo.blockgamesquare.vo.WalletVo;
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
public class CentWalletController {
    @Autowired
    private ICentWalletService centWalletService;
    @Autowired
    private ITransferService transferService;

    /**
     * 用户中心化钱包列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/centWallet", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo centWalletPage(@ModelAttribute("qo") UserQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = centWalletService.centWalletPage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * excel导出用户中心化钱包列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/centWallet/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出用户中心化钱包信息")
    public void exportInviterData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=centWalletInfo.xls");
        //查询需要的用户信息
        List<WalletVo> walletList = centWalletService.exportCentWalletData();
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("用户名");
        hander.createCell(1).setCellValue("手机号");
        hander.createCell(2).setCellValue("ETH钱包余额");
        hander.createCell(3).setCellValue("EOS钱包余额");
        hander.createCell(4).setCellValue("BGS钱包余额");
        for (int i = 0; i < walletList.size(); i++){
            WalletVo vo = walletList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            currentRow.createCell(0).setCellValue(vo.getUserBasic().getNickName());
            currentRow.createCell(1).setCellValue(vo.getUserBasic().getPhoneNumber());
            if (vo.getEthtoken() != null){
                currentRow.createCell(2).setCellValue(vo.getEthtoken().getAmount());
            }
            if (vo.getEostoken() != null){
                currentRow.createCell(3).setCellValue(vo.getEostoken().getAmount());
            }
            if (vo.getBgstoken() != null){
                currentRow.createCell(4).setCellValue(vo.getBgstoken().getAmount());
            }
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }

    /**
     * 用户中心化钱包交易明细
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/centWalletItem", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo centWalletItem(@ModelAttribute("qo") WalletItemQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = transferService.centWalletItemPage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }


    /**
     * excel导出用户中心化钱包交易明细
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/centWalletItem/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出用户中心化钱包交易明细")
    public void exportCentWalletItemData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=centWalletItemInfo.xls");
        //查询需要的用户信息
        List<Transfer> transferList = transferService.exportCentWalletItemData();
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("用户名");
        hander.createCell(1).setCellValue("手机号");
        hander.createCell(2).setCellValue("时间");
        hander.createCell(3).setCellValue("交易类型");
        hander.createCell(4).setCellValue("货币类型");
        hander.createCell(5).setCellValue("数量");
        hander.createCell(6).setCellValue("交易结果");
        for (int i = 0; i < transferList.size(); i++){
            Transfer transfer = transferList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            currentRow.createCell(0).setCellValue(transfer.getUserBasic().getNickName());
            currentRow.createCell(1).setCellValue(transfer.getUserBasic().getPhoneNumber());
            String createdTime = DateUtil.dateToStrLong(transfer.getCreatedTime()); //转化成string
            currentRow.createCell(2).setCellValue(createdTime);
            switch (transfer.getTransferType()){
                case 8:
                    currentRow.createCell(3).setCellValue("提现--中心钱包转到链上钱包");
                    break;
                case 9:
                    currentRow.createCell(3).setCellValue("注册获得BGS");
                    break;
                case 10:
                    currentRow.createCell(3).setCellValue("邀请获得BGS");
                    break;
                case 11:
                    currentRow.createCell(3).setCellValue("锁仓获得收益");
                    break;
                case 12:
                    currentRow.createCell(3).setCellValue("代理获得收益");
                    break;
            }
            switch (transfer.getTokenType()){
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
            currentRow.createCell(5).setCellValue(transfer.getAmount());
            switch (transfer.getStatus()){
                case 0:
                    currentRow.createCell(6).setCellValue("失败");
                    break;
                case 1:
                    currentRow.createCell(6).setCellValue("成功");
                    break;
            }
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }
}
