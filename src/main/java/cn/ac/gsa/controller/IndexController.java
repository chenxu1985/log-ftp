package cn.ac.gsa.controller;

import cn.ac.gsa.send.SendWe;
import cn.ac.gsa.utility.HttpRequestUtil;
import cn.ac.gsa.utility.SSHUtil;
import com.jcraft.jsch.JSchException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author chenx
 */
@RestController
public class IndexController {

    @Scheduled(cron = "0 0 10 * * ?") //每天10点钟执行
//    @Scheduled(cron = "0 */1 * * * ?")//每1分钟监测
    public void autoFtp() throws Exception {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-1);
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
        Date dt1=rightNow.getTime();
        String date = sdf1.format(dt1);
        logFtpController log = new logFtpController();
        log.transHTTPFile(date);
//        log.transHTTPFile("2021-12-15");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));

    }

}
