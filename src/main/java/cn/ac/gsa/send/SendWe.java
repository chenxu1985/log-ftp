package cn.ac.gsa.send;

import cn.ac.gsa.code.DownLoad;
import cn.ac.gsa.code.Downtime;
import cn.ac.gsa.code.Index;
import cn.ac.gsa.code.Log;
import cn.ac.gsa.model.IacsUrlDataVo;
import cn.ac.gsa.utility.SSHUtil;
import cn.ac.gsa.utility.SendWeChatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author chenx
 */
@RestController
public class SendWe {
    public static void sendLog(String userId,String content) {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        try {
            String token = msgUtils.getToken(Log.CORPID, Log.CORPSECRET);
            String postdata = msgUtils.createpostdata(userId, "text", Log.APPLICATION_ID, "content", content);
            String resp = msgUtils.post(Log.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
//            String resp = msgUtils.post(Downtime.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendUserId_Url(), "{\"mobile\":\"15210356745\"}", token);
            System.out.println("获取到的token======>" + token);
//            System.out.println("请求数据======>" + postdata);
            System.out.println("发送微信的响应数据======>" + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendDownTime(String userId,String content) {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        try {
            String token = msgUtils.getToken(Downtime.CORPID, Downtime.CORPSECRET);
            String postdata = msgUtils.createpostdata(userId, "text", Downtime.APPLICATION_ID, "content", content);
            String resp = msgUtils.post(Downtime.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
//            String resp = msgUtils.post(Downtime.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendUserId_Url(), "{\"mobile\":\"15210356745\"}", token);
            System.out.println("获取到的token======>" + token);
//            System.out.println("请求数据======>" + postdata);
            System.out.println("发送微信的响应数据======>" + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendDownLoad(String userId,String content) {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        try {
            String token = msgUtils.getToken(DownLoad.CORPID, DownLoad.CORPSECRET);
            String postdata = msgUtils.createpostdata(userId, "text", DownLoad.APPLICATION_ID, "content", content);
            String resp = msgUtils.post(DownLoad.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
//            String resp = msgUtils.post(Downtime.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendUserId_Url(), "{\"mobile\":\"15210356745\"}", token);
            System.out.println("获取到的token======>" + token);
//            System.out.println("请求数据======>" + postdata);
            System.out.println("发送微信的响应数据======>" + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendIndex(String userId,String content) {
        SendWeChatUtils msgUtils = new SendWeChatUtils();
        try {
            String token = msgUtils.getToken(Index.CORPID, Index.CORPSECRET);
            String postdata = msgUtils.createpostdata(userId, "text", Index.APPLICATION_ID, "content", content);
            String resp = msgUtils.post(Index.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendMessage_Url(), postdata, token);
//            String resp = msgUtils.post(Downtime.CHARSET, msgUtils.CONTENT_TYPE, (new IacsUrlDataVo()).getSendUserId_Url(), "{\"mobile\":\"15210356745\"}", token);
            System.out.println("获取到的token======>" + token);
//            System.out.println("请求数据======>" + postdata);
            System.out.println("发送微信的响应数据======>" + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
