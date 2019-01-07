package com.jiazhuo.blockgamesquare.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import lombok.*;

/**
 * 需要导入的依赖
 *
 * 将下边的依赖放到maven项目的 pom.xml 中：

         <dependency>
         <groupId>com.gexin.platform</groupId>
         <artifactId>gexin-rp-sdk-http</artifactId>
         <version>4.1.0.0</version>
         </dependency>

 然后再增加一个repository到 pom.xml 中：

         <repositories>
         <repository>
         <id>getui-nexus</id>
         <url>http://mvn.gt.igexin.com/nexus/content/repositories/releases/</url>
         </repository>
         </repositories>
 *
 *
 *
 * **/
public class PushtoSingleDeee {
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "edsJ02bj187dY9s55UGzG6";
    private static String appKey = "rT7vwXLTU79neQtKlUtvf9";
    private static String masterSecret = "cv7O22Xrq79icwbrIygBn1";

//    static String CID = "69ae53040472f98831a738f0f69c646a";
    //别名推送方式
//    static String Alias = "UID";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * * 标题：BGS注册成功
     *  * 内容：您已成功注册BGS平台，免费获得平台赠送的EOS钱包。出售虚拟币，请联系币商：（名称）+（手机号）+（微信号）
     *
     * @param CID 在登录接口中 前台传过来的CID
     * @param sendMessage 需要发送的消息(从后台查询,需要从数据库表中查,这里需要新建一张表)
     * @param title 消息的标题
     * @return
     */
    public boolean push(String CID, String sendMessage, String title){
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        LinkTemplate template = linkTemplateDemo(sendMessage,title);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
//        target.setAlias(Alias);
        IPushResult ret = null;
        try {
            //TODO 需要从数据库中查币商的信息 设置标题
            ret = push.pushMessageToSingle(message, target);
            if(ret.getResponse().get("result").equals("ok")){
                return true;
            }else {
                return false;
            }
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        return true;
    }

    public static LinkTemplate linkTemplateDemo(String sendMessage,String title) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(sendMessage);
        style.setText(title);
        // 配置通知栏图标
        //TODO  图标另给
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 设置打开的网址地址
      //  template.setUrl("http://www.baidu.com");
        return template;
    }

}

