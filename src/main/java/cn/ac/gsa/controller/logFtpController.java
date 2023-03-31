package cn.ac.gsa.controller;

import cn.ac.gsa.send.SendEmail;
import cn.ac.gsa.send.SendWe;
import cn.ac.gsa.utility.SSHUtil;
import cn.ac.gsa.utils.SSHUtils;
import cn.ac.gsa.utility.HttpRequestUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * author chenx
 */
@RestController
public class logFtpController {

    @GetMapping("/log-all/{begin}")
    public void transHTTPFile(@PathVariable(value = "begin") String begin) throws Exception {
        this.gsaAllLog(begin);
        this.ncovAllLog(begin);
        this.projectAllLog(begin);
        this.sampleAllLog(begin);
        this.dataBaseAllLog(begin);
        this.dogAllLog(begin);
        this.edkAllLog(begin);
        this.humanAllLog(begin);
        this.gsubAllLog(begin);
        this.gvmAllLog(begin);
        this.gwasAllLog(begin);
        this.gwhAllLog(begin);
        this.ic4rAllLog(begin);
        this.lgcAllLog(begin);
        this.methbankAllLog(begin);
        this.P1AllLog(begin);
        this.P2AllLog(begin);
        this.pedAllLog(begin);
        this.redAllLog(begin);
        this.ftp123AllLog(begin);
        this.ftp124AllLog(begin);
        this.loginfo(begin);
        this.bigdinfo(begin);
        this.ngdcloginfo(begin);
        this.ftplogAllLog(begin);
        this.ngdcftpAllLog(begin);
        this.gsaUpload();
    }

    @GetMapping("/log-cat/{begin}")
    public void catFile(@PathVariable(value = "begin") String begin) throws Exception {
        this.loginfo(begin);
        this.bigdinfo(begin);
        this.ngdcloginfo(begin);
        this.ftplogAllLog(begin);
        this.ngdcftpAllLog(begin);
    }
    @GetMapping("/log-ftp1/{begin}")
    public void ftpFile1(@PathVariable(value = "begin") String begin) throws Exception {
        this.ftplogAllLog(begin);
    }
    @GetMapping("/log-ftp2/{begin}")
    public void ftpFile2(@PathVariable(value = "begin") String begin) throws Exception {
        this.ngdcftpAllLog(begin);
    }
    @GetMapping("/log-gsa/{begin}")
    public void gsaAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
            String path = "/webdb/gsagroup/webServers/logs";
            String toPath = "/disk/webdb/csdb/logs/gsalogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_gsav2_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，gsa日志获取失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("GSA log trans ok");
    }
    @GetMapping("/log-ncov/{begin}")
    public void ncovAllLog(@PathVariable(value = "begin") String begin) {
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.19", "virus", "rcov19@NGDC", 22);
            String path = "/home/virus/log/accessLog";
            String toPath = "/disk/webdb/csdb/logs/2019nCoVRlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"ncovr_access_");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.19，2019nCoVRlogs日志获取失败请联系邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("Ncov log trans ok");
    }
    @GetMapping("/log-project/{begin}")
    public void projectAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
            String path = "/webdb/gsagroup/webServers/logs";
            String toPath = "/disk/webdb/csdb/logs/bioprojectlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_bioproject_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，project日志获取失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("project log trans ok");
    }
    @GetMapping("/log-sample/{begin}")
    public void sampleAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
            String path = "/webdb/gsagroup/webServers/logs";
            String toPath = "/disk/webdb/csdb/logs/biosamplelogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_biosample_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，sample日志获取失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("sample log trans ok");
    }
    @GetMapping("/log-database/{begin}")
    public void dataBaseAllLog(@PathVariable(value = "begin") String begin) {
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.16", "root", "bigd@79root@@NGDC", 22);
            String path = "/disk1/webdb/webApplications/dbcommons/accessLog";
            String toPath = "/disk/webdb/csdb/logs/dbcommonslogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"dbcommons_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.16，database日志获取失败请联系英克，邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("database log trans ok");
    }
    @GetMapping("/log-dog/{begin}")
    public void dogAllLog(@PathVariable(value = "begin") String begin) {
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.17", "webdb", "76_wEb%2021@nGdC", 22);
            String path = "/data1/webdb/webServer_idog/update/apache-tomcat-8.5.82_dogsdv3/logs";
            String toPath = "/disk/webdb/csdb/logs/doglogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"localhost_dogsdv3_access_log");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.17，dog日志获取失败请联系唐碧霞"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("dog log trans ok");
    }
    @GetMapping("/log-edk/{begin}")
    public void edkAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.19", "web", "if[]23NcLa91", 22);
            String path = "/home/web/logs/accessLog";
            String toPath = "/disk/webdb/csdb/logs/edklogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"edk_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.19，edk日志获取失败请联系邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("edk log trans ok");
    }
    @GetMapping("/log-human/{begin}")
    public void humanAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
            String path = "/webdb/gsagroup/webServers/logs/gsahuman";
            String toPath = "/disk/webdb/csdb/logs/gsahumanlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_human_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，human日志获取失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("human log trans ok");
    }
    @GetMapping("/log-gsub/{begin}")
    public void gsubAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
            String path = "/webdb/gsagroup/webServers/logs";
            String toPath = "/disk/webdb/csdb/logs/gsublogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_gsub_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，gsub日志获取失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("gsub log trans ok");
    }
    @GetMapping("/log-gvm/{begin}")
    public void gvmAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.14", "datatrans", "trans%2021!BigD", 22);
            String path = "/disk1/webdb/webApplications/logs/gvm_net1";
            String toPath = "/disk/webdb/csdb/logs/gvmlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"gvm_access_log2");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.14，gvm日志获取失败请联系唐碧霞"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("Gvm log trans ok");
    }
    @GetMapping("/log-gwas/{begin}")
    public void gwasAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.14", "datatrans", "trans%2021!BigD", 22);
            String path = "/disk1/webdb/webApplications/bigd_gwas_atlas_20220605/logs";
            String toPath = "/disk/webdb/csdb/logs/gwaslogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"gwas_access_");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.14，gwas日志获取失败请联系冬梅"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("gwas log trans ok");
    }
    @GetMapping("/log-gwh/{begin}")
    public void gwhAllLog(@PathVariable(value = "begin") String begin) {
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.91", "gwh", "nTm9#Zbv?C9Z6tQp", 22);
            String path = "/disk/gwh/access_logs";
            String toPath = "/disk/webdb/csdb/logs/gwhlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"gwh_access_log");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.91，gwh日志获取失败请联系马英克"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("Gwh log trans ok");
    }
    @GetMapping("/log-ic4r/{begin}")
    public void ic4rAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
            String path = "/disk1/webdb/webApplications/ic4r/logs";
            String toPath = "/disk/webdb/csdb/logs/ic4rlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"ic4r_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.16，ic4r日志获取失败请联系英克，邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("ic4r log trans ok");
    }
    @GetMapping("/log-lgc/{begin}")
    public void lgcAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
            String path = "/disk1/webdb/webApplications/bigd/wangfan/logs";
            String toPath = "/disk/webdb/csdb/logs/lgclogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"lgc_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.16，lgc日志获取失败请联系英克，邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("lgc log trans ok");
    }
    @GetMapping("/log-methbank/{begin}")
    public void methbankAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.19", "web", "if[]23NcLa91", 22);
            String path = "/home/web/logs/accessLog";
            String toPath = "/disk/webdb/csdb/logs/methbanklogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"methbank_v5_access_");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.19，methbank日志获取失败请联系邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("methbank log trans ok");
    }
    @GetMapping("/log-p1/{begin}")
    public void P1AllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.19", "web", "if[]23NcLa91", 22);
            String path = "/home/web/logs";
            String toPath = "/disk/webdb/csdb/logs/ngdcP1logs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"bigd_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.19，ngdc1日志获取失败请联系邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("ngdcP1 log trans ok");
    }
    @GetMapping("/log-p2/{begin}")
    public void P2AllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
            String path = "/disk1/webdb/webApplications/bigd/bigd-web/apache-tomcat-9.0.24/logs";
            String toPath = "/disk/webdb/csdb/logs/ngdcP2logs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"bigd_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.16，ngdc2日志获取失败请联系邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("ngdcP2 log trans ok");
    }
    @GetMapping("/log-red/{begin}")
    public void redAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.23", "bigd", "HzDy0105$Gyy", 22);
            String path = "/wrd/srv/ic4r-expression/tomcat9068/logs";
            String toPath = "/disk/webdb/csdb/logs/redlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"red_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.23，red日志获取失败请联系邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("Red log trans ok");
    }
    @GetMapping("/log-ped/{begin}")
    public void pedAllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.164.23", "bigd", "HzDy0105$Gyy", 22);
            String path = "/home/bigd/logs";
            String toPath = "/disk/webdb/csdb/logs/pedlogs";
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"ped_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.23，ped日志获取失败请联系邹东"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("Ped log trans ok");
    }
    @GetMapping("/log-ftp123/{begin}")
    public void ftp123AllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.166.13", "ftpTransfer", "ftpgsa123", 22);
            String path = "/var/log/vsftpd";
            String toPath1 = "/disk/webdb/csdb/logs/ftp123logs";
            String toPath2 = "/disk/webdb/csdb/logs/ftpngdc123logs";
            Vector logList= gsa.listFiles(path);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List dataList = getBetweenDates(sdf.parse(begin),new Date());
		for(int i = 0;i<dataList.size();i++) {
			Date date = (Date) dataList.get(i);
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String strDate = sf.format(date);
			String fileName = "xferlog"+"-"+strDate;
            gsa.downloadFile(path+"/"+fileName,toPath1);
            gsa.downloadFile(path+"/"+fileName,toPath2);
		}
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.13，ftp123日志获取失败"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("123 log trans ok");
    }
    @GetMapping("/log-ftp124/{begin}")
    public void ftp124AllLog(@PathVariable(value = "begin") String begin){
        try {
            SSHUtils gsa = new SSHUtils("192.168.166.14", "ftpTransfer", "ftpTra¥124%2023@", 22);
            String path = "/var/log/vsftpd/";
            String toPath1 = "/disk/webdb/csdb/logs/ftp124logs";
            String toPath2 = "/disk/webdb/csdb/logs/ftpngdc124logs";
            Vector logList= gsa.listFiles(path);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List dataList = getBetweenDates(sdf.parse(begin),new Date());
            for(int i = 0;i<dataList.size();i++) {
                Date date = (Date) dataList.get(i);
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                String strDate = sf.format(date);
                String fileName = "xferlog"+"-"+strDate;
                gsa.downloadFile(path+"/"+fileName,toPath1);
                gsa.downloadFile(path+"/"+fileName,toPath2);
            }
            gsa.closeSession();
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.14，ftp124日志获取失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
        System.out.println("124 log trans ok");
    }
    @GetMapping("/log-loginfo/{begin}")
    public void loginfo(@PathVariable(value = "begin") String begin)  {
        try {
            SSHUtils gsa = new SSHUtils("192.168.130.12", "webdb", "web12@ngdc$2021!",22);
            String path = "/disk/webdb/csdb/logs/rice2logs";
            String toPath = "/disk/webdb/csdb/logs/rice2logs";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List dataList = getBetweenDates(sdf.parse(begin),new Date());
            for(int i = 0;i<dataList.size();i++) {
                Date date = (Date) dataList.get(i);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sf.format(date);
                String fileName = "rice2_access_log"+"."+strDate+".txt";
                String cmdGsa ="";
                cmdGsa = "/disk/webdb/csdb/logs/gsalogs/big_gsav2_access_"+strDate+".log";
                File f = new File(cmdGsa);
                if(!f.exists()){
                    cmdGsa = "/disk/webdb/csdb/logs/gsalogs/big_gsav2_access."+strDate+".log";

                }
                String cmdGwh = "/disk/webdb/csdb/logs/gwhlogs/gwh_access_log."+strDate+".log";
                String cmdDog = "/disk/webdb/csdb/logs/doglogs/dogsd_access_log."+strDate+".log";
                File fd = new File(cmdDog);
                if(!fd.exists()){
                    cmdDog = "/disk/webdb/csdb/logs/doglogs/dogsd_access_log."+strDate+".txt";
                    File txtDog = new File(cmdDog);
                    if(!txtDog.exists()){
                        cmdDog = "/disk/webdb/csdb/logs/doglogs/localhost_dogsdv3_access_log."+strDate+".txt";
                    }
                }
                String cmdGvm = "/disk/webdb/csdb/logs/gvmlogs/gvm_access_log2."+strDate+".log";
                String cmd = "cat "+cmdGsa+" "+cmdGwh+" "+cmdDog+" "+cmdGvm+" >"+path+"/"+fileName +" &";
                System.out.println(cmd);
                gsa.execCommandByJSch(cmd);
                Thread.sleep(5000);
            }
            gsa.closeSession();
            System.out.println("rice log trans ok");
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.130.12，loginfo日志整合失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    @GetMapping("/log-bigdinfo/{begin}")
    public void bigdinfo(@PathVariable(value = "begin") String begin) {
        try {
            SSHUtils gsa = new SSHUtils("192.168.130.12", "webdb", "web12@ngdc$2021!",22);
            String path = "/disk/webdb/csdb/logs/bigdlogs";
            String toPath = "/disk/webdb/csdb/logs/bigdlogs";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List dataList = getBetweenDates(sdf.parse(begin),new Date());
            for(int i = 0;i<dataList.size();i++) {
                Date date = (Date) dataList.get(i);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sf.format(date);
                String fileName = "bigd_access_log"+"."+strDate+".txt";
                String cmdGsa ="";
                cmdGsa = "/disk/webdb/csdb/logs/gsalogs/big_gsav2_access_"+strDate+".log";
                File f = new File(cmdGsa);
                if(!f.exists()){
                    cmdGsa = "/disk/webdb/csdb/logs/gsalogs/big_gsav2_access."+strDate+".log";
                }
                String cmdGwh = "/disk/webdb/csdb/logs/gwhlogs/gwh_access_log."+strDate+".log";
                String cmdGvm = "/disk/webdb/csdb/logs/gvmlogs/gvm_access_log2."+strDate+".log";
                String cmdMeth = "/disk/webdb/csdb/logs/methbanklogs/methbank_access_."+strDate+".log";
                File logMeth = new File(cmdMeth);
                if(!logMeth.exists()){
                    cmdMeth =  "/disk/webdb/csdb/logs/methbanklogs/methbank_v5_access_."+strDate+".log";
                }
                String cmdncovr = "/disk/webdb/csdb/logs/2019nCoVRlogs/ncovr_access_."+strDate+".log";
                String cmd = "cat "+cmdGsa+" "+cmdGwh+" "+cmdGvm+" "+cmdMeth+" "+cmdncovr+">"+path+"/"+fileName +" &";
                System.out.println(cmd);
                gsa.execCommandByJSch(cmd);
                Thread.sleep(5000);
            }
            gsa.closeSession();
            System.out.println("bigd log trans ok");
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.130.12，bigdinfo日志整合失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    @GetMapping("/log-ngdcinfo/{begin}")
    public void ngdcloginfo(@PathVariable(value = "begin") String begin) {
        try{
            SSHUtils gsa = new SSHUtils("192.168.130.12", "webdb", "web12@ngdc$2021!",22);
            String path = "/disk/webdb/csdb/logs/ngdclogs";
            String toPath = "/disk/webdb/csdb/logs/ngdclogs";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List dataList = getBetweenDates(sdf.parse(begin),new Date());
            for(int i = 0;i<dataList.size();i++) {
                Date date = (Date) dataList.get(i);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sf.format(date);
                String fileName = "ngdc_access_log"+"."+strDate+".txt";
                String cmdGsa ="";
                cmdGsa = "/disk/webdb/csdb/logs/gsalogs/big_gsav2_access_"+strDate+".log";
                File f = new File(cmdGsa);
                if(!f.exists()){
                    cmdGsa = "/disk/webdb/csdb/logs/gsalogs/big_gsav2_access."+strDate+".log";
                }
                String cmdGwh = "/disk/webdb/csdb/logs/gwhlogs/gwh_access_log."+strDate+".log";
                String cmdMeth = "/disk/webdb/csdb/logs/methbanklogs/methbank_access_."+strDate+".log";
                File logMeth = new File(cmdMeth);
                if(!logMeth.exists()){
                    cmdMeth =  "/disk/webdb/csdb/logs/methbanklogs/methbank_v5_access_."+strDate+".log";
                }
                String cmdGvm = "/disk/webdb/csdb/logs/gvmlogs/gvm_access_log2."+strDate+".log";
                String cmdngdc1 = "/disk/webdb/csdb/logs/ngdcP1logs/bigd_access."+strDate+".log";
                String cmdngdc2 = "/disk/webdb/csdb/logs/ngdcP2logs/bigd_access."+strDate+".log";
                String cmdncovr = "/disk/webdb/csdb/logs/2019nCoVRlogs/ncovr_access_."+strDate+".log";
                String cmdgsub = "/disk/webdb/csdb/logs/gsublogs/big_gsub_access_"+strDate+".log";
                File fgsub = new File(cmdgsub);
                if(!fgsub.exists()){
                    cmdgsub = "/disk/webdb/csdb/logs/gsublogs/big_gsub_access_."+strDate+".log";
                }
                String cmddbcommons = "/disk/webdb/csdb/logs/dbcommonslogs/dbcommons_access."+strDate+".log";
                String cmdlgc = "/disk/webdb/csdb/logs/lgclogs/lgc_access."+strDate+".log";
                String cmdgwas = "/disk/webdb/csdb/logs/gwaslogs/gwas_atlas_access_log."+strDate+".log";
                File fgwas = new File(cmdgwas);
                if(!fgwas.exists()){
                    cmdgwas = "/disk/webdb/csdb/logs/gwaslogs/gwas_access_."+strDate+".log";
                }
                String cmdic4r = "/disk/webdb/csdb/logs/ic4rlogs/ic4r_access."+strDate+".log";
                String cmdbioproject = "/disk/webdb/csdb/logs/bioprojectlogs/big_bioproject_access_"+strDate+".log";
                File fproject = new File(cmdbioproject);
                if(!fproject.exists()){
                    cmdbioproject = "/disk/webdb/csdb/logs/bioprojectlogs/big_bioproject_access_."+strDate+".log";
                }
                String cmdbiosample = "/disk/webdb/csdb/logs/biosamplelogs/big_biosample_access_"+strDate+".log";
                File fsample = new File(cmdbiosample);
                if(!fsample.exists()){
                    cmdbiosample = "/disk/webdb/csdb/logs/biosamplelogs/big_biosample_access_."+strDate+".log";
                }
                String cmdedk = "/disk/webdb/csdb/logs/edklogs/edk_access."+strDate+".log";
                String cmdped = "/disk/webdb/csdb/logs/pedlogs/ped_access."+strDate+".log";
                String cmdred = "/disk/webdb/csdb/logs/redlogs/red_access."+strDate+".log";
                String cmdhuman = "/disk/webdb/csdb/logs/gsahumanlogs/big_human_access."+strDate+".log";

                String cmd = "cat "+cmdGsa+" "+cmdGwh+" "+cmdGvm+" "+cmdMeth+" "+cmdngdc1+" "+cmdngdc2+" "+cmdncovr+" "+cmdgsub+" "+cmddbcommons+" "+cmdlgc+" "+cmdgwas+" "+cmdic4r+" "+cmdbioproject+" " +
                        ""+cmdbiosample+" "+cmdedk+" "+cmdped+" "+cmdred+" "+cmdhuman+" >"+path+"/"+fileName +" &";
                System.out.println(cmd);
                gsa.execCommandByJSch(cmd);
//            gsa.exeCommand(cmd);
                Thread.sleep(5000);
            }
            gsa.closeSession();
            System.out.println("ngdc log trans ok");
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.130.12，ngdc日志整合失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    @GetMapping("/log-ftp/{begin}")
    public void ftplogAllLog(@PathVariable(value = "begin") String begin){
        try{
            SSHUtils gsa = new SSHUtils("192.168.130.12", "webdb", "web12@ngdc$2021!", 22);
            String path = "/disk/webdb/csdb/logs/ngdclogs";
            String toPath = "/disk/webdb/csdb/logs/ngdclogs";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List dataList = getBetweenDates(sdf.parse(begin),new Date());
            for(int i = 0;i<dataList.size();i++) {
                Date date = (Date) dataList.get(i);
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                String strDate = sf.format(date);
                String strCp = "cat /disk/webdb/csdb/logs/ftp123logs/xferlog-"+strDate+" /disk/webdb/csdb/logs/ftp124logs/xferlog-"+strDate+" >/disk/webdb/csdb/logs/ftplogs/xferlog-"+strDate+".txt" ;
                System.out.println(strCp);
                gsa.execCommandByJSch(strCp);
                Thread.sleep(5000);
            }
            gsa.closeSession();
            System.out.println("ftp log trans ok");
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.130.12，Ftp日志整合失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    @GetMapping("/log-ngdcftp/{begin}")
    public void ngdcftpAllLog(@PathVariable(value = "begin") String begin){
        try{
            SSHUtils gsa = new SSHUtils("192.168.130.12", "webdb", "web12@ngdc$2021!", 22);
            String path = "/csdb/logs/ftpngdclogs";
            String toPath = "/csdb/logs/ftpngdclogs";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List dataList = getBetweenDates(sdf.parse(begin),new Date());
            for(int i = 0;i<dataList.size();i++) {
                Date date = (Date) dataList.get(i);
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                String strDate = sf.format(date);
                String strCp = "cat /disk/webdb/csdb/logs/ftpngdc123logs/xferlog-"+strDate+" /disk/webdb/csdb/logs/ftpngdc124logs/xferlog-"+strDate+" >/disk/webdb/csdb/logs/ftpngdclogs/xferlog-"+strDate+".txt" ;
                System.out.println(strCp);
                gsa.execCommandByJSch(strCp);
                Thread.sleep(5000);
            }
            gsa.closeSession();
            System.out.println("ngdcftp log trans ok");
        }catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.130.12，ngdcFtp日志整合失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    private List<Date> getBetweenDates(Date begin, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
            /* Calendar tempEnd = Calendar.getInstance();
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            tempEnd.setTime(end);
            while (tempStart.before(tempEnd)) {
                result.add(tempStart.getTime());
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }*/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        while(begin.getTime()<=yesterday.getTime()){
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }
    public HashMap getGsaNameMap(Vector fileList,String h_name){
        HashMap nameMap = new HashMap();
        HashMap datamap = new HashMap();
        int length = 0;
        for(Object v : fileList){
            ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry)v;
            String file_name = lsEntry.getFilename();
            file_name = file_name.split("/")[file_name.split("/").length-1];
            //读取所有属于gsa的log文件
            if(file_name.contains(h_name+"_")&&file_name.contains(".log")) {
                String data_name = "";
                String data_name_sp="";
                if(file_name.contains("_.")){
                     data_name_sp = file_name.split("\\.log")[0];
                } else {
                     data_name_sp = file_name.split("\\.")[0];
                }
                data_name = data_name_sp.substring(data_name_sp.length()-10,data_name_sp.length());
                nameMap.put(data_name,file_name);
                if(datamap.size()!=0) {
                    String data_name_old = datamap.get("data").toString();
                    if(data_name.compareTo(data_name_old)>0) {
                        datamap.put("data",data_name);
                    }
                } else {
                    datamap.put("data",data_name);
                }
                length++;
            } else if(file_name.contains(h_name+".")&&file_name.contains(".log")){
                String data_name = "";
                String data_name_sp = file_name.split("\\.log")[0];
                data_name = data_name_sp.substring(data_name_sp.length()-10,data_name_sp.length());
                nameMap.put(data_name,file_name);
                if(datamap.size()!=0) {
                    String data_name_old = datamap.get("data").toString();
                    if(data_name.compareTo(data_name_old)>0) {
                        datamap.put("data",data_name);
                    }
                } else {
                    datamap.put("data",data_name);
                }
                length++;
            } else if(file_name.contains(h_name)&&file_name.contains(".txt")) {
                String data_name = "";
                String data_name_sp = file_name.split("\\.")[1];
                data_name = data_name_sp.substring(data_name_sp.length()-10,data_name_sp.length());
                //System.out.println(data_name);
                nameMap.put(data_name,file_name);
                if(datamap.size()!=0) {
                    String data_name_old = datamap.get("data").toString();
                    if(data_name.compareTo(data_name_old)>0) {
                        datamap.put("data",data_name);
                    }
                } else {
                    datamap.put("data",data_name);
                }
                length++;
            }
        }
        return nameMap;
    }
    public int downloadFile(String path,String toPath,String begin,SSHUtils sshUtils,HashMap nameMap) {
        for (Object key : nameMap.keySet()){
			String strKey = (String)key;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date dt0= null;
            Date dt1= null;
            try {
                dt0 = sdf.parse(begin);
                dt1 = sdf.parse(strKey);
                if(dt1.getTime()>dt0.getTime()) {
                    String fileName = (String) nameMap.get(key);
                    sshUtils.downloadFile(path+"/"+fileName,toPath);
                    System.out.println(fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return 0;
    }
    @Scheduled(cron = "0 0 1 * * ?")
    @GetMapping("/gsaUpload")
    public void gsaUpload() throws Exception {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String ip = "192.168.164.20";
        String ip2 = "192.168.130.13";
        String username = "gsagroup";
        String pass = "gsa@big35$2019!";
        String path = "/webdb/gsagroup/webApplications/gsub_20190729/gsub/upload";
//        String toPath = "/csdb/upload/gsa/upload";
        String toPath = "/disk/webdb/csdb/upload/gsa/upload";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String localPath = toPath + "/" + date;
        try{
            File f = new File(localPath);
            if (!f.exists()) {
                f.mkdirs();
                Vector logList = gsa.listFiles(path);
                for (Object v : logList) {
                    ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) v;
                    String filename = lsEntry.getFilename();
                    if (filename.equals(".") || filename.equals("..")) {
                        continue;
                    }
                    File f1 = new File(localPath + "/" + filename);
                    f1.mkdirs();
                    Vector logList1 = gsa.listFiles(path + "/" + filename);
                    for (Object v1 : logList1) {
                        ChannelSftp.LsEntry lsEntry1 = (ChannelSftp.LsEntry) v1;
                        String filename1 = lsEntry1.getFilename();
                        if (filename1.equals(".") || filename1.equals("..")) {
                            continue;
                        }
                        File f2 = new File(localPath + "/" + filename+"/"+filename1);
                        f2.mkdirs();
                        Vector logList2 = gsa.listFiles(path + "/" + filename+"/"+filename1);
                        for (Iterator<ChannelSftp.LsEntry> iterator = logList2.iterator(); iterator.hasNext(); ) {
                            ChannelSftp.LsEntry str = iterator.next();
                            String filename2 = str.getFilename();
                            if (filename2.equals(".") || filename2.equals("..")) {
                                continue;
                            }
                            gsa.downloadFile(path + "/" + filename + "/" + filename1+"/"+filename2, localPath + "/" + filename+"/"+filename1+"/"+filename2);
                        }
                    }
                }
            }
        }catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，gsaUpload文件备份失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    @Scheduled(cron = "0 0 2 * * ?")
    @GetMapping("/gsaSampleUpload")
    public void gsaSampleUpload() throws Exception {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String ip = "192.168.164.20";
        String ip2 = "192.168.130.13";
        String username = "gsagroup";
        String pass = "gsa@big35$2019!";
        String path = "/webdb/gsagroup/webApplications/gsub_20190729/gsub/sampleUpload";
//        String toPath = "/csdb/upload/gsa/upload";
        String toPath = "/disk/webdb/csdb/upload/gsa/sampleUpload";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            String date = sdf.format(new Date());
            String localPath = toPath + "/" + date;
            File f = new File(localPath);
            if (!f.exists()) {
                f.mkdirs();
                Vector logList = gsa.listFiles(path);
                for (Object v : logList) {
                    ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) v;
                    String filename = lsEntry.getFilename();
                    if (filename.equals(".") || filename.equals("..")) {
                        continue;
                    }
                    File f1 = new File(localPath + "/" + filename);
                    f1.mkdirs();
                    Vector logList1 = gsa.listFiles(path + "/" + filename);
                    for (Iterator<ChannelSftp.LsEntry> iterator = logList1.iterator(); iterator.hasNext(); ) {
                        ChannelSftp.LsEntry str = iterator.next();
                        String filename1 = str.getFilename();
                        if (filename1.equals(".") || filename1.equals("..")) {
                            continue;
                        }
                        gsa.downloadFile(path + "/" + filename + "/" + filename1, localPath + "/" + filename+"/"+filename1);
                    }
                }
            }
        }catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，gsaSampleUpload文件备份失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    @Scheduled(cron = "0 0 3 * * ?")
    @GetMapping("/gsaHumanUpload")
    public void gsaHumanUpload() throws Exception {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String ip = "192.168.164.20";
        String ip2 = "192.168.130.13";
        String username = "gsagroup";
        String pass = "gsa@big35$2019!";
        String path = "/webdb/gsagroup/webApplications/gsa_human_20200410/gsa-human/Upload/meta";
//        String toPath = "/csdb/upload/gsa/upload";
        String toPath = "/disk/webdb/csdb/upload/gsa-human/upload";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            String localPath = toPath + "/" + date;
            File f = new File(localPath);
            if (!f.exists()) {
                f.mkdirs();
                Vector logList = gsa.listFiles(path);
                for (Object v : logList) {
                    ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) v;
                    String filename = lsEntry.getFilename();
                    if (filename.equals(".") || filename.equals("..")) {
                        continue;
                    }
                    File f1 = new File(localPath + "/" + filename);
                    f1.mkdirs();
                    Vector logList1 = gsa.listFiles(path + "/" + filename);
                    for (Iterator<ChannelSftp.LsEntry> iterator = logList1.iterator(); iterator.hasNext(); ) {
                        ChannelSftp.LsEntry str = iterator.next();
                        String filename1 = str.getFilename();
                        if (filename1.equals(".") || filename1.equals("..")) {
                            continue;
                        }
                        gsa.downloadFile(path + "/" + filename + "/" + filename1, localPath + "/" + filename+"/"+filename1);
                    }
                }
            }
        }catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SendEmail.send("chenx@big.ac.cn", "IP：192.168.164.20，gsaHumanUpload文件备份失败请联系陈旭"+dateFormat.format(new Date()), getStackTraceInfo(e));
        }
    }
    public static String getStackTraceInfo(Throwable ex){
        StringWriter sw=new StringWriter();

        try(PrintWriter pw=new PrintWriter(sw);){
            ex.printStackTrace(pw);
            return sw.toString();
        }
    }
}
