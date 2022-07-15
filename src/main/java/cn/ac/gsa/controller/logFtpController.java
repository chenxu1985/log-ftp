package cn.ac.gsa.controller;

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

    public void gsaAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String path = "/webdb/gsagroup/webServers/logs";
        String toPath = "/csdb/logs/gsalogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_gsav2_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("GSA log trans ok");
    }
    public void ncovAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.19", "virus", "rcov19@NGDC", 22);
        String path = "/home/virus/log/accessLog";
        String toPath = "/csdb/logs/2019nCoVRlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"ncovr_access_");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ncov log trans ok");
    }
    public void projectAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String path = "/webdb/gsagroup/webServers/logs";
        String toPath = "/csdb/logs/bioprojectlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_bioproject_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("project log trans ok");
    }
    public void sampleAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String path = "/webdb/gsagroup/webServers/logs";
        String toPath = "/csdb/logs/biosamplelogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_biosample_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sample log trans ok");
    }
    public void dataBaseAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
        String path = "/disk1/webdb/webApplications/dbcommons/accessLog";
        String toPath = "/csdb/logs/dbcommonslogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"dbcommons_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("database log trans ok");
    }
    public void dogAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.17", "datatrans", "7Tran6S%2021ngDC!", 22);
        String path = "/data1/webdb/webServer_idog/apache-tomcat-8.5.66_dogsdv3/logs";
        String toPath = "/csdb/logs/doglogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"dogsdv2_access_log");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("dog log trans ok");
    }
    public void edkAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.19", "web", "if[]23NcLa91", 22);
        String path = "/home/web/logs/accessLog";
        String toPath = "/csdb/logs/edklogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"edk_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("edk log trans ok");
    }
    public void humanAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String path = "/webdb/gsagroup/webServers/logs/gsahuman";
        String toPath = "/csdb/logs/gsahumanlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_human_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("human log trans ok");
    }
    public void gsubAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String path = "/webdb/gsagroup/webServers/logs";
        String toPath = "/csdb/logs/gsublogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"big_gsub_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("gsub log trans ok");
    }
    public void gvmAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.14", "datatrans", "trans%2021!BigD", 22);
        String path = "/disk1/webdb/webApplications/logs/gvm_net1";
        String toPath = "/csdb/logs/gvmlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"gvm_access_log");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Gvm log trans ok");
    }
    public void gwasAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.14", "datatrans", "trans%2021!BigD", 22);
        String path = "/disk1/webdb/webApplications/logs/gwas_atlas";
        String toPath = "/csdb/logs/gwaslogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"gwas_atlas_access_log");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("gwas log trans ok");
    }
    public void gwhAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
        String path = "/disk1/webdb/webApplications/warehouse/access_logs";
        String toPath = "/csdb/logs/gwhlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"gwh_access_log");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Gwh log trans ok");
    }
    public void ic4rAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
        String path = "/disk1/webdb/webApplications/ic4r/logs";
        String toPath = "/csdb/logs/ic4rlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"ic4r_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ic4r log trans ok");
    }
    public void lgcAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
        String path = "/disk1/webdb/webApplications/bigd/wangfan/logs";
        String toPath = "/csdb/logs/lgclogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"lgc_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("lgc log trans ok");
    }
    public void methbankAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.19", "web", "if[]23NcLa91", 22);
        String path = "/home/web/logs/accessLog";
        String toPath = "/csdb/logs/methbanklogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"methbank_access_");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("methbank log trans ok");
    }
    public void P1AllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.19", "web", "if[]23NcLa91", 22);
        String path = "/home/web/logs";
        String toPath = "/csdb/logs/ngdcP1logs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"bigd_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ngdcP1 log trans ok");
    }
    public void P2AllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.16", "webdb", "bigd@webdb##ngdc", 22);
        String path = "/disk1/webdb/webApplications/bigd/bigd-web/apache-tomcat-9.0.24/logs";
        String toPath = "/csdb/logs/ngdcP2logs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"bigd_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ngdcP2 log trans ok");
    }
    public void redAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.23", "bigd", "HzDy0105$Gyy", 22);
        String path = "/wrd/srv/ic4r-expression/tomcat8/logs";
        String toPath = "/csdb/logs/redlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"red_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Red log trans ok");
    }
    public void pedAllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.164.23", "bigd", "HzDy0105$Gyy", 22);
        String path = "/home/bigd/logs";
        String toPath = "/csdb/logs/pedlogs";
        try {
            Vector logList= gsa.listFiles(path);
            HashMap nameMap= getGsaNameMap(logList,"ped_access");
            int v = downloadFile(path,toPath,begin,gsa,nameMap);
            gsa.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ped log trans ok");
    }
    public void ftp123AllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.166.13", "ftpTransfer", "ftpgsa123", 22);
        String path = "/var/log/vsftpd";
        String toPath1 = "/csdb/logs/ftp123logs";
        String toPath2 = "/csdb/logs/ftpngdc123logs";
        try {
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
            e.printStackTrace();
        }
        System.out.println("123 log trans ok");
    }
    public void ftp124AllLog(String begin) throws IOException {
        SSHUtils gsa = new SSHUtils("192.168.166.14", "ftpTransfer", "FTpgAs#%124@2022!", 22);
        String path = "/var/log/vsftpd/";
        String toPath1 = "/csdb/logs/ftp124logs";
        String toPath2 = "/csdb/logs/ftpngdc124logs";
        try {
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
            e.printStackTrace();
        }
        System.out.println("124 log trans ok");
    }
    public void loginfo(String begin) throws IOException, ParseException, InterruptedException, JSchException {
        SSHUtils gsa = new SSHUtils("192.168.130.13", "webdb", "web75@big$2019!",22);
        String path = "/csdb/logs/rice2logs";
        String toPath = "/csdb/logs/rice2logs";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List dataList = getBetweenDates(sdf.parse(begin),new Date());
        for(int i = 0;i<dataList.size();i++) {
            Date date = (Date) dataList.get(i);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sf.format(date);
            String fileName = "rice2_access_log"+"."+strDate+".txt";
            String cmdGsa ="";
            cmdGsa = "/csdb/logs/gsalogs/big_gsav2_access_"+strDate+".log";
            File f = new File(cmdGsa);
            if(!f.exists()){
                cmdGsa = "/csdb/logs/gsalogs/big_gsav2_access."+strDate+".log";

            }
            String cmdGwh = "/csdb/logs/gwhlogs/gwh_access_log."+strDate+".log";
            String cmdDog = "/csdb/logs/doglogs/dogsd_access_log."+strDate+".log";
            File fd = new File(cmdDog);
            if(!fd.exists()){
                cmdDog = "/csdb/logs/doglogs/dogsd_access_log."+strDate+".txt";
            }
            String cmdGvm = "/csdb/logs/gvmlogs/gvm_access_log."+strDate+".log";
            String cmd = "cat "+cmdGsa+" "+cmdGwh+" "+cmdDog+" "+cmdGvm+" >"+path+"/"+fileName +" &";
            System.out.println(cmd);
            gsa.execCommandByJSch(cmd);
            Thread.sleep(5000);
        }
        gsa.closeSession();
        System.out.println("rice log trans ok");
    }
    public void bigdinfo(String begin) throws IOException, ParseException, InterruptedException, JSchException {
        SSHUtils gsa = new SSHUtils("192.168.130.13", "webdb", "web75@big$2019!",22);
        String path = "/csdb/logs/bigdlogs";
        String toPath = "/csdb/logs/bigdlogs";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List dataList = getBetweenDates(sdf.parse(begin),new Date());
        for(int i = 0;i<dataList.size();i++) {
            Date date = (Date) dataList.get(i);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sf.format(date);
            String fileName = "bigd_access_log"+"."+strDate+".txt";
            String cmdGsa ="";
            cmdGsa = "/csdb/logs/gsalogs/big_gsav2_access_"+strDate+".log";
            File f = new File(cmdGsa);
            if(!f.exists()){
                cmdGsa = "/csdb/logs/gsalogs/big_gsav2_access."+strDate+".log";
            }
            String cmdGwh = "/csdb/logs/gwhlogs/gwh_access_log."+strDate+".log";
            String cmdGvm = "/csdb/logs/gvmlogs/gvm_access_log."+strDate+".log";
            String cmdMeth = "/csdb/logs/methbanklogs/methbank_access_."+strDate+".log";
            String cmdncovr = "/csdb/logs/2019nCoVRlogs/ncovr_access_."+strDate+".log";
            String cmd = "cat "+cmdGsa+" "+cmdGwh+" "+cmdGvm+" "+cmdMeth+" "+cmdncovr+">"+path+"/"+fileName +" &";
            System.out.println(cmd);
            gsa.execCommandByJSch(cmd);
            Thread.sleep(5000);
        }
        gsa.closeSession();
        System.out.println("bigd log trans ok");
    }
    public void ngdcloginfo(String begin) throws IOException, ParseException, InterruptedException, JSchException {
        SSHUtils gsa = new SSHUtils("192.168.130.13", "webdb", "web75@big$2019!",22);
        String path = "/csdb/logs/ngdclogs";
        String toPath = "/csdb/logs/ngdclogs";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List dataList = getBetweenDates(sdf.parse(begin),new Date());
        for(int i = 0;i<dataList.size();i++) {
            Date date = (Date) dataList.get(i);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sf.format(date);
            String fileName = "ngdc_access_log"+"."+strDate+".txt";
            String cmdGsa ="";
            cmdGsa = "/csdb/logs/gsalogs/big_gsav2_access_"+strDate+".log";
            File f = new File(cmdGsa);
            if(!f.exists()){
                cmdGsa = "/csdb/logs/gsalogs/big_gsav2_access."+strDate+".log";
            }
            String cmdGwh = "/csdb/logs/gwhlogs/gwh_access_log."+strDate+".log";
            String cmdMeth = "/csdb/logs/methbanklogs/methbank_access_."+strDate+".log";
            String cmdGvm = "/csdb/logs/gvmlogs/gvm_access_log."+strDate+".log";
            String cmdngdc1 = "/csdb/logs/ngdcP1logs/bigd_access."+strDate+".log";
            String cmdngdc2 = "/csdb/logs/ngdcP2logs/bigd_access."+strDate+".log";
            String cmdncovr = "/csdb/logs/2019nCoVRlogs/ncovr_access_."+strDate+".log";
            String cmdgsub = "/csdb/logs/gsublogs/big_gsub_access_"+strDate+".log";
            File fgsub = new File(cmdgsub);
            if(!fgsub.exists()){
                cmdgsub = "/csdb/logs/gsublogs/big_gsub_access_."+strDate+".log";
            }
            String cmddbcommons = "/csdb/logs/dbcommonslogs/dbcommons_access."+strDate+".log";
            String cmdlgc = "/csdb/logs/lgclogs/lgc_access."+strDate+".log";
            String cmdgwas = "/csdb/logs/gwaslogs/gwas_atlas_access_log."+strDate+".log";
            String cmdic4r = "/csdb/logs/ic4rlogs/ic4r_access."+strDate+".log";
            String cmdbioproject = "/csdb/logs/bioprojectlogs/big_bioproject_access_"+strDate+".log";
            File fproject = new File(cmdbioproject);
            if(!fproject.exists()){
                cmdbioproject = "/csdb/logs/bioprojectlogs/big_bioproject_access_."+strDate+".log";
            }
            String cmdbiosample = "/csdb/logs/biosamplelogs/big_biosample_access_"+strDate+".log";
            File fsample = new File(cmdbiosample);
            if(!fsample.exists()){
                cmdbiosample = "/csdb/logs/biosamplelogs/big_biosample_access_."+strDate+".log";
            }
            String cmdedk = "/csdb/logs/edklogs/edk_access."+strDate+".log";
            String cmdped = "/csdb/logs/pedlogs/ped_access."+strDate+".log";
            String cmdred = "/csdb/logs/redlogs/red_access."+strDate+".log";
            String cmdhuman = "/csdb/logs/gsahumanlogs/big_human_access."+strDate+".log";

            String cmd = "cat "+cmdGsa+" "+cmdGwh+" "+cmdGvm+" "+cmdMeth+" "+cmdngdc1+" "+cmdngdc2+" "+cmdncovr+" "+cmdgsub+" "+cmddbcommons+" "+cmdlgc+" "+cmdgwas+" "+cmdic4r+" "+cmdbioproject+" " +
                    ""+cmdbiosample+" "+cmdedk+" "+cmdped+" "+cmdred+" "+cmdhuman+" >"+path+"/"+fileName +" &";
            System.out.println(cmd);
            gsa.execCommandByJSch(cmd);
//            gsa.exeCommand(cmd);
            Thread.sleep(5000);
        }
        gsa.closeSession();
        System.out.println("ngdc log trans ok");
    }
    public void ftplogAllLog(String begin) throws IOException, ParseException, InterruptedException, JSchException {
        SSHUtils gsa = new SSHUtils("192.168.130.13", "webdb", "web75@big$2019!", 22);
        String path = "/csdb/logs/ngdclogs";
        String toPath = "/csdb/logs/ngdclogs";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List dataList = getBetweenDates(sdf.parse(begin),new Date());
        for(int i = 0;i<dataList.size();i++) {
            Date date = (Date) dataList.get(i);
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String strDate = sf.format(date);
            String strCp = "cat /csdb/logs/ftp123logs/xferlog-"+strDate+" /csdb/logs/ftp124logs/xferlog-"+strDate+" >/csdb/logs/ftplogs/xferlog-"+strDate+".txt" ;
            System.out.println(strCp);
            gsa.execCommandByJSch(strCp);
            Thread.sleep(5000);
        }
        gsa.closeSession();
        System.out.println("ftp log trans ok");
    }

    public void ngdcftpAllLog(String begin) throws IOException, ParseException, InterruptedException, JSchException {
        SSHUtils gsa = new SSHUtils("192.168.130.13", "webdb", "web75@big$2019!", 22);
        String path = "/csdb/logs/ftpngdclogs";
        String toPath = "/csdb/logs/ftpngdclogs";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List dataList = getBetweenDates(sdf.parse(begin),new Date());
        for(int i = 0;i<dataList.size();i++) {
            Date date = (Date) dataList.get(i);
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String strDate = sf.format(date);
            String strCp = "cat /csdb/logs/ftpngdc123logs/xferlog-"+strDate+" /csdb/logs/ftpngdc124logs/xferlog-"+strDate+" >/csdb/logs/ftpngdclogs/xferlog-"+strDate+".txt" ;
            System.out.println(strCp);
            gsa.execCommandByJSch(strCp);
            Thread.sleep(5000);
        }
        gsa.closeSession();
        System.out.println("ngdcftp log trans ok");
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
        while(begin.getTime()<=end.getTime()){
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
    public void gsaUpload() throws Exception {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String ip = "192.168.164.20";
        String ip2 = "192.168.130.13";
        String username = "gsagroup";
        String pass = "gsa@big35$2019!";
        String path = "/webdb/gsagroup/webApplications/gsub_20190729/gsub/upload";
//        String toPath = "/csdb/upload/gsa/upload";
        String toPath = "/csdb/upload/gsa/upload";
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
    }
    @Scheduled(cron = "0 0 2 * * ?")
    public void gsaSampleUpload() throws Exception {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String ip = "192.168.164.20";
        String ip2 = "192.168.130.13";
        String username = "gsagroup";
        String pass = "gsa@big35$2019!";
        String path = "/webdb/gsagroup/webApplications/gsub_20190729/gsub/sampleUpload";
//        String toPath = "/csdb/upload/gsa/upload";
        String toPath = "/csdb/upload/gsa/sampleUpload";
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
    }
    @Scheduled(cron = "0 0 3 * * ?")
    public void gsaHumanUpload() throws Exception {
        SSHUtils gsa = new SSHUtils("192.168.164.20", "gsagroup", "gsa@big35$2019!", 22);
        String ip = "192.168.164.20";
        String ip2 = "192.168.130.13";
        String username = "gsagroup";
        String pass = "gsa@big35$2019!";
        String path = "/webdb/gsagroup/webApplications/gsa_human_20200410/gsa-human/Upload/meta";
//        String toPath = "/csdb/upload/gsa/upload";
        String toPath = "/csdb/upload/gsa-human/upload";
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
    }

}
