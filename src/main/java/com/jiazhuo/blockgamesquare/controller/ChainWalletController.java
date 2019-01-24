package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Transfer;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.qo.WalletItemQueryObject;
import com.jiazhuo.blockgamesquare.service.IChainWalletService;
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
public class ChainWalletController {
    @Autowired
    private IChainWalletService chainWalletService;
    @Autowired
    private ITransferService transferService;

    /**
     * 用户链上钱包列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/chainWallet", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo chainWalletPage(@ModelAttribute("qo") UserQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = chainWalletService.chainWalletPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * excel导出用户链上钱包列表
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/users/chainWallet/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出用户链上钱包列表")
    public void exportInviterData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=chainWalletInfo.xls");
        //查询需要的用户信息
        List<WalletVo> walletList = chainWalletService.exportChainWalletData();
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("用户名");
        hander.createCell(1).setCellValue("手机号");
        hander.createCell(2).setCellValue("ETH钱包地址");
        hander.createCell(3).setCellValue("EOS钱包地址");
        hander.createCell(4).setCellValue("BGS钱包地址");
        for (int i = 0; i < walletList.size(); i++){
            WalletVo vo = walletList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            currentRow.createCell(0).setCellValue(vo.getUserBasic().getNickName());
            currentRow.createCell(1).setCellValue(vo.getUserBasic().getPhoneNumber());
            if (vo.getEthtoken() != null){
                currentRow.createCell(2).setCellValue(vo.getEthtoken().getETHAddress());
            }
            if (vo.getEostoken() != null){
                currentRow.createCell(3).setCellValue(vo.getEostoken().getEOSAccountName());
            }
            if (vo.getBgstoken() != null){
                currentRow.createCell(4).setCellValue(vo.getBgstoken().getETHAddress());
            }
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }

    /**
     * 用户链上钱包交易明细
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/chainWalletItem", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo chainWalletItem(@ModelAttribute("qo") WalletItemQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = transferService.chainWalletItemPage(qo);
        vo.setResult(result);
        return vo;
    }


    /**
     * excel导出用户链上钱包交易明细
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/users/chainWalletItem/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出用户链上钱包交易明细")
    public void exportChainWalletItemData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=chainWalletItemInfo.xls");
        //查询需要的用户信息
        List<Transfer> transferList = transferService.exportChainWalletItemData();
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
            currentRow.createCell(0).setCellValue(transfer.getNickName());
            currentRow.createCell(1).setCellValue(transfer.getPhoneNumber());
            String createdTime = DateUtil.dateToStrLong(transfer.getCreatedTime()); //转化成string
            currentRow.createCell(2).setCellValue(createdTime);
            switch (transfer.getTransferType()){
                case 1:
                    currentRow.createCell(3).setCellValue("链上钱包转到中心钱包");
                    break;
                case 2:
                    currentRow.createCell(3).setCellValue("链上钱包转到链上钱包");
                    break;
                case 3:
                    currentRow.createCell(3).setCellValue("锁仓支出");
                    break;
                case 4:
                    currentRow.createCell(3).setCellValue("Dapp游戏支出");
                    break;
                case 5:
                    currentRow.createCell(3).setCellValue("购买代理人支出");
                    break;
                case 6:
                    currentRow.createCell(3).setCellValue("质押EOS的RAM支出 ");
                    break;
                case 7:
                    currentRow.createCell(3).setCellValue("质押EOS的CPU支出");
                    break;
                case 8:
                    currentRow.createCell(3).setCellValue("质押EOS的NET支出");
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
                case 1:
                    currentRow.createCell(6).setCellValue("进行中");
                    break;
                case 2:
                    currentRow.createCell(6).setCellValue("成功");
                    break;
                case 3:
                    currentRow.createCell(6).setCellValue("失败");
                    break;
            }
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }
}
