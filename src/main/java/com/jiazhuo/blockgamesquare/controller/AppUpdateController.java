package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Optconf;
import com.jiazhuo.blockgamesquare.service.IOptconfService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.StringUtil;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppUpdateController {
    @Autowired
    private IOptconfService optconfService;


    /**
     * IOS版本信息
     * @return
     */
    @RequestMapping(value = "/mgrsite/appUpdate/ios", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo appUpdateIOS(){
        JSONResultVo vo = new JSONResultVo();
        List<Optconf> optconfList = optconfService.queryList();
        List<Optconf> ios = new ArrayList<>();
        for (Optconf op : optconfList) {
            switch (op.getConfName()){
                case "IOS_APP_LOG":
                    op.setExplain("更新日志");
                    ios.add(op);
                    break;
                case "IOS_APP_VERSION":
                    op.setExplain("版本号");
                    ios.add(op);
                    break;
                case "IOS_IOS_ID":
                    op.setExplain("appid");
                    ios.add(op);
                    break;
                case "IOS_IS_FORCE":
                    op.setExplain("是否强制更新");
                    ios.add(op);
                    break;
                case "IOS_VERSION_SIZE":
                    op.setExplain("安装包大小");
                    ios.add(op);
                    break;
                case "IOS_IS_GROUNDING":
                    op.setExplain("是否隐藏苹果拒绝上架的东西");
                    ios.add(op);
                    break;
            }
        }
        vo.setResult(ios);
        return vo;
    }

    /**
     * android版本信息
     * @return
     */
    @RequestMapping(value = "/mgrsite/appUpdate/android", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo appUpdateAndroid(){
        JSONResultVo vo = new JSONResultVo();
        List<Optconf> optconfList = optconfService.queryList();
        List<Optconf> android = new ArrayList<>();
        for (Optconf op : optconfList) {
            switch (op.getConfName()){
                case "ANDROID_APP_PATH":
                    op.setExplain("apk路径");
                    android.add(op);
                    break;
                case "ANDROID_APP_VERSION":
                    op.setExplain("app版本");
                    android.add(op);
                    break;
                case "ANDROID_APP_LOG":
                    op.setExplain("更新内容");
                    android.add(op);
                    break;
                case "ANDROID_IS_FORCE":
                    op.setExplain("是否强制更新");
                    android.add(op);
                    break;
                case "ANDROID_VERSION_SIZE":
                    op.setExplain("安装包大小");
                    android.add(op);
                    break;
            }
        }
        vo.setResult(android);
        return vo;
    }

    /**
     * 更新ios版本
     * @param appLog
     * @param appVersion
     * @param iosId
     * @param isForce
     * @param versionSize
     * @param isGrounding
     * @return
     */
    @RequestMapping(value = "/mgrsite/appUpdate/updateIos", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("更新ios版本")
    public JSONResultVo updateIos(String appLog, String appVersion, String iosId, String isForce, String versionSize, String isGrounding){
        if (StringUtil.isNull(appLog)){
            return JSONResultVo.error("appLog不能为空");
        }
        if (StringUtil.isNull(appVersion)){
            return JSONResultVo.error("appVersion不能为空");
        }
        if (StringUtil.isNull(iosId)){
            return JSONResultVo.error("iosId不能为空");
        }
        if (StringUtil.isNull(isForce)){
                return JSONResultVo.error("isForce不能为空");
        }
        if (StringUtil.isNull(versionSize)){
                return JSONResultVo.error("versionSize不能为空");
        }
        if (StringUtil.isNull(isGrounding)){
            return JSONResultVo.error("isGrounding不能为空");
        }
        optconfService.updateConf("IOS_APP_LOG", appLog);
        optconfService.updateConf("IOS_APP_VERSION", appVersion);
        optconfService.updateConf("IOS_IOS_ID", iosId);
        optconfService.updateConf("IOS_IS_FORCE", isForce);
        optconfService.updateConf("IOS_VERSION_SIZE", versionSize);
        optconfService.updateConf("IOS_IS_GROUNDING", isGrounding);
        return JSONResultVo.ok("更新成功");
    }


    /**
     * 更新android版本
     * @param appLog
     * @param appVersion
     * @param isForce
     * @param versionSize
     * @param appPath
     * @return
     */
    @RequestMapping(value = "/mgrsite/appUpdate/updateAndroid", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("更新android版本")
    public JSONResultVo updateAndroid(String appLog, String appVersion, String isForce, String versionSize, String appPath){
        if (StringUtil.isNull(appLog)){
            return JSONResultVo.error("appLog不能为空");
        }
        if (StringUtil.isNull(appVersion)){
            return JSONResultVo.error("appVersion不能为空");
        }
        if (StringUtil.isNull(isForce)){
            return JSONResultVo.error("isForce不能为空");
        }
        if (StringUtil.isNull(versionSize)){
            return JSONResultVo.error("versionSize不能为空");
        }
        if (StringUtil.isNull(appPath)){
            return JSONResultVo.error("appPath不能为空");
        }
        optconfService.updateConf("ANDROID_APP_LOG", appLog);
        optconfService.updateConf("ANDROID_APP_VERSION", appVersion);
        optconfService.updateConf("ANDROID_IS_FORCE", isForce);
        optconfService.updateConf("ANDROID_VERSION_SIZE", versionSize);
        optconfService.updateConf("ANDROID_APP_PATH", appPath);
        return JSONResultVo.ok("更新成功");
    }


    /**
     * 获取版本信息(ios)
     * @return
     */
    @RequestMapping(value = "/appsite/appUpdateIos", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult ios(){
        List<Optconf> optconfList = optconfService.queryList();
        Map<String, String> map = new HashMap<>();
        for (Optconf op : optconfList) {
            if (op.getConfName().equals("IOS_APP_LOG")){
                map.put("app_log", op.getConfValue());
            }
            if (op.getConfName().equals("IOS_APP_VERSION")){
                map.put("app_version", op.getConfValue());
            }
            if (op.getConfName().equals("IOS_IOS_ID")){
                map.put("ios_id", op.getConfValue());
            }
            if (op.getConfName().equals("IOS_IS_FORCE")){
                map.put("is_force", op.getConfValue());
            }
            if (op.getConfName().equals("IOS_VERSION_SIZE")){
                map.put("version_size", op.getConfValue());
            }
            if (op.getConfName().equals("IOS_IS_GROUNDING")){
                map.put("is_grounding", op.getConfValue());
            }
        }
        SuperResult res = SuperResult.ok(map);
        res.setMsg("获取ios版本信息成功");
        return res;
    }

    /**
     * 获取版本信息(android)
     * @return
     */
    @RequestMapping(value = "/appsite/appUpdateAndroid", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult android(){
        List<Optconf> optconfList = optconfService.queryList();
        Map<String, String> map = new HashMap<>();
        for (Optconf op : optconfList) {
            if (op.getConfName().equals("ANDROID_APP_PATH")){
                map.put("app_path", op.getConfValue());
            }
            if (op.getConfName().equals("ANDROID_APP_VERSION")){
                map.put("app_version", op.getConfValue());
            }
            if (op.getConfName().equals("ANDROID_APP_LOG")){
                map.put("app_log", op.getConfValue());
            }
            if (op.getConfName().equals("ANDROID_IS_FORCE")){
                map.put("is_force", op.getConfValue());
            }
            if (op.getConfName().equals("ANDROID_VERSION_SIZE")){
                map.put("version_size", op.getConfValue());
            }
        }
        SuperResult res = SuperResult.ok(map);
        res.setMsg("获取android版本信息成功");
        return res;
    }
}
