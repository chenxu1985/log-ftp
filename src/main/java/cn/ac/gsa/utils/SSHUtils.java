package cn.ac.gsa.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import cn.ac.gsa.send.SendWe;
import com.jcraft.jsch.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ch.qos.logback.core.net.AbstractSocketAppender.DEFAULT_PORT;

public class SSHUtils {
    private static SSHUtils sshUtils;

    private SSHUtils() {}
    public static synchronized SSHUtils getInstance() {
        if (sshUtils == null) {
            sshUtils = new SSHUtils();
        }
        return sshUtils;
    }

    private static String remoteip="";
    private static String remoteusername="";
    private static String remotepasswd="";
    private static final int DEFAULT_PORT = 22;
    private static int remoteport;

    private Session session;
    private boolean logined = false;

    public SSHUtils (String ip,String username,String pwd, int port)
    {
        super();
        try {
            this.remoteip = ip;
            this.remoteusername = username;
            this.remotepasswd = pwd;
            this.remoteport = port;
            this.getConnection(ip,username,pwd);
        } catch (Exception e) {
            SendWe.sendLog("ChenXu","IP："+ip+"，无法链接");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
            e.printStackTrace();
        }
    }

    public void getConnection(String remoteip, String remoteusername, String remotepasswd) throws Exception {
        if (logined) { return;}
        JSch jSch = new JSch();
        try {
            session = jSch.getSession(SSHUtils.remoteusername, SSHUtils.remoteip, remoteport);
            session.setPassword(SSHUtils.remotepasswd);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            logined = true;

        } catch (JSchException e) {
            SendWe.sendLog("ChenXu","IP："+remoteip+"，无法链接");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
            logined = false;
            throw new Exception("try connect failed  "+ SSHUtils.remoteip +",username: "+ SSHUtils.remoteusername +",password: "+ SSHUtils.remotepasswd);//+",port: "+remoteport);
        }
    }

    public void closeSession() {
        if (session != null) {
            session.disconnect();
        }
    }

    public void fileDownload(String remoteFile,String localFilePath) throws JSchException, SftpException {
        System.out.println(remoteFile+ "  filedownload222 "+ localFilePath);
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.get(remoteFile, localFilePath);
        channelSftp.quit();
        System.out.println("ddd " + remoteFile + " ,下载文件成功.....");
    }

    public int downloadFile(String remoteFile,String localFilePath) throws JSchException, SftpException {
        int res = 0;
        try {
            SSHUtils.getInstance().getConnection(remoteip, remoteusername, remotepasswd);
            System.out.println("地址: " + remoteip + ",账号: " + remoteusername + ",连接成功.....");
            System.out.println(remoteFile+ "  filedownload111 "+ localFilePath);
            fileDownload(remoteFile, localFilePath);

            //SSHUtils.getInstance().closeSession();

        } catch (Exception e) {
            SendWe.sendLog("ChenXu","IP："+remoteip+"，无法下载请确认权限");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
            res = -1;
            System.err.println("远程连接失败......");
            e.printStackTrace();
        }
        return res;

    }

    public Vector listFiles(String directory) throws JSchException, SftpException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 显示目录信息
        Vector ls = channelSftp.ls(directory);
        //System.out.println("5、" + ls);
        // 切断连接
        channelSftp.exit();
        return ls;
    }
    public  void execCommandByJSch(String command) throws IOException,JSchException{
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        channelExec.disconnect();
    }
    /**
     * 创建目录文件夹
     * @param directory  要创建文件夹的位置路径
     * @param fileName  要创建文件夹的名称
     */
    public void mkdir(String directory,String fileName) throws JSchException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        try {
            sftp.cd(directory);
            sftp.mkdir(fileName);
            System.out.println("文件夹创建成功");
        } catch (Exception e) {
            System.out.println("文件夹创建失败");
            e.printStackTrace();
        }
    }
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException, JSchException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

}
