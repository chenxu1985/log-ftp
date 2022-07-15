package cn.ac.gsa.utils;

import ch.ethz.ssh2.*;

import java.io.*;
import java.util.Vector;

public class SSHUtil {
	
	private static String remoteip="";
	private static String remoteusername="";
	private static String remotepasswd="";
	private Connection conng =null;
	public SSHUtil(String ip, String username, String pwd)
	{
		remoteip = ip;
		remoteusername = username;
		remotepasswd = pwd;
	}
	
	public Connection getConnection() throws IOException{
		try{
			conng=new Connection(remoteip);
			conng.connect();
			conng.authenticateWithPassword(remoteusername, remotepasswd);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return conng;
	}
	
	public void downloadFile(String sourcefile,String path) throws Exception{		
		Connection conn = null;
		try{
			conn = getConnection();
			if(conn!=null)
			{
				SCPClient sp = new SCPClient(conn);
				sp.get(sourcefile, path);

			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
	}
	
	public int uploadFile(String localFile,String remotePath) throws Exception{
		Connection conn = null;
		int res = 0;
		try{
		conn = getConnection();
		SCPClient sp = new SCPClient(conn);
		sp.put(localFile, remotePath);

		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			res = -1;
		}
		return res;
	}
	
	public void exeMapping(String cmd) throws IOException {
		Connection conn = null;
		Session sess = null;
		String line = "";
		try {
			conn = getConnection();
			sess = conn.openSession();
			sess.requestPTY("bash");
			sess.startShell();
			OutputStreamWriter writer;
			writer = new OutputStreamWriter(sess.getStdin());
			writer.write(cmd + "\n");
			writer.flush();
			writer.close();

			InputStream out = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(out));
			while (true) {
				line = br.readLine();
				if (line.equals("allfinish")){
					Thread.sleep(500);
					break;
				}
				System.out.println(line);
			}
			br.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sess.close();
		}
	}

	public void exeDataProcess(String cmd) throws IOException
	{
		Connection conn = null;
		Session sess = null;
		String line="";
		try {
			conn = getConnection();
			sess = conn.openSession();
			sess.requestPTY("bash");
			sess.startShell();
			OutputStreamWriter writer;
			writer = new OutputStreamWriter(sess.getStdin());
			writer.write( cmd+ "\n");
			writer.flush();
			
			Thread.sleep(500);
			
			InputStream out = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(out));
			writer.write( "echo \"allfinish\""+ "\n");
			writer.flush();
			writer.close();
			while (true) {
				line = br.readLine();
				if (line.equals("allfinish")){
					Thread.sleep(500);
					break;
				}
				System.out.println(line);
			}
			br.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sess.close();
		}
		
	}
	
	public void exeDataProcess1()
	{
		
		
	}
	
	
	public String  exeCommand(String cmd) throws IOException
	{
		Connection conn = null;
		Session sess=null;

		String line ="";
		try{
		conn = getConnection();
			sess=conn.openSession();
			sess.execCommand(cmd);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally{
			if(sess!=null)
				sess.close();	
		}	
		return line;
	}
	
	public int connClose()
	{
		if(conng!=null)
			conng.close();
		return 0;
		
	}
	
		
	public String exeQstatCommand(String cmd,String jobid) throws IOException{
		Connection conn = null;
		Session sess=null;
		 String line ="";
		try{
		conn = getConnection();
		sess=conn.openSession();
		sess.execCommand(cmd);
		
		InputStream stdout = new StreamGobbler(sess.getStdout());

	    BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
	   
	   
	    while((line =br.readLine())!=null)
	    {
	    	if(line.contains(jobid))
	    	{
	    		break;
	    	}	
	    	System.out.println(line);
	    }	
		sess.close();
		conn.close();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally{			
			sess.close();
			conn.close();
		}		
		return line;
	}
	
	
	
	
	
	public String[] listDir(String dir) throws IOException{
		Connection conn = null;
		conn = getConnection();
		SFTPv3Client sc=new SFTPv3Client(conn);
		Vector vc=sc.ls(dir);
		String[] result=new String[vc.size()];
		for(int i=0;i<result.length;i++){
			result[i]=((SFTPv3DirectoryEntry)(vc.get(i))).filename;
		}
		sc.close();
		conn.close();
		return result;
	}
}
