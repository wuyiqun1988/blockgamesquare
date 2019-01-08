package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.LockWarehouse;
import com.jiazhuo.blockgamesquare.qo.LockWarehouseQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.service.ILockWarehouseService;
import com.jiazhuo.blockgamesquare.util.DateUtil;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
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
public class LockWarehouseController {
    @Autowired
    private ILockWarehouseService lockWarehouseService;


    /**
     * 锁仓订单
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/lockOrder", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo lockOrderPage(@ModelAttribute("qo") LockWarehouseQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = lockWarehouseService.lockOrderPage(qo);
        vo.setResult(result);
        return vo;
    }


    /**
     * excel导出锁仓订单列表
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/mgrsite/lockOrder/exportData", method = RequestMethod.GET)
    @RequiredPermission("excel导出锁仓订单列表")
    public void exportLockOrderData(HttpServletResponse response) throws IOException {
        //设置想下载的响应头信息
        response.addHeader("Content-disposition", "attachment;filename=LockOrderInfo.xls");
        //查询需要的用户信息
        List<LockWarehouse> lockWarehouseList = lockWarehouseService.exportLockOrderData();
        //创建一个excel工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作页签sheet
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow hander = sheet.createRow(0);
        //创建单元格信息
        hander.createCell(0).setCellValue("锁仓时间");
        hander.createCell(1).setCellValue("用户名");
        hander.createCell(2).setCellValue("手机号");
        hander.createCell(3).setCellValue("数量");
        hander.createCell(4).setCellValue("锁仓时长");
        hander.createCell(5).setCellValue("货币类型");
        hander.createCell(6).setCellValue("锁仓状态");
        hander.createCell(7).setCellValue("收益");
        for (int i = 0; i < lockWarehouseList.size(); i++){
            LockWarehouse lockWarehouse = lockWarehouseList.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            //创建单元格信息
            String createdTime = DateUtil.dateToStrLong(lockWarehouse.getCreatedTime()); //转化成string
            currentRow.createCell(0).setCellValue(createdTime);
            currentRow.createCell(1).setCellValue(lockWarehouse.getNickName());
            currentRow.createCell(2).setCellValue(lockWarehouse.getPhoneNumber());
            currentRow.createCell(3).setCellValue(lockWarehouse.getAmount());
            currentRow.createCell(4).setCellValue(lockWarehouse.getPeriod());
            switch (lockWarehouse.getTokenType()){
                case 1:
                    currentRow.createCell(5).setCellValue("ETH");
                    break;
                case 2:
                    currentRow.createCell(5).setCellValue("EOS");
                    break;
                case 3:
                    currentRow.createCell(5).setCellValue("BGS");
                    break;
            }
            switch (lockWarehouse.getStatus()){
                case 1:
                    currentRow.createCell(6).setCellValue("收益中");
                    break;
                case 2:
                    currentRow.createCell(6).setCellValue("归仓中");
                    break;
                case 3:
                    currentRow.createCell(6).setCellValue("已结束");
                    break;
            }
            currentRow.createCell(7).setCellValue(lockWarehouse.getFinalProfit());
        }
        workbook.write(response.getOutputStream());
        //关闭流操作
        workbook.close();
    }

    /**
     * 归仓中列表(等待解仓)
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/waitUnlockOrder", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo unlockOrderPage(@ModelAttribute("qo") LockWarehouseQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = lockWarehouseService.unlockOrderPage(qo);
        vo.setResult(result);
        return vo;
    }

}
