package com.ouzy.util.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp下载文件到本地电脑工具类
 * @author OUZY
 * 
 *
 */
public class FtpUtil {

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
			String ftpPassword, int ftpPort) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				System.out.println("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				System.out.println("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FTP的端口错误,请正确配置。");
		}
		return ftpClient;
	}

	public static void main(String[] args) {
		String ftp_ipadd = "127.0.0.1";// ftp 地址
		String ftp_user = "test";// ftp 登录帐号
		String ftp_passwd = "test";// ftp 登录帐号密码
		int ftpport = 21;// ftp端口，默认为21
		FTPClient ftpClient = getFTPClient(ftp_ipadd, ftp_user, ftp_passwd, ftpport);// 连接ftp
		OutputStream os = null;
		try {
			System.out.println(String.valueOf(ftpClient.getReplyCode()));
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置文件类型
			ftpClient.enterLocalPassiveMode();// 设置ftp 模式，有被动模式和活动模式，这里设置为被动模式
			String datestr = "201904";// 设置本地目录
			ftpClient.changeWorkingDirectory("/bigData/201904/");// 设置ftp文件所在的目录
			FTPFile[] files = ftpClient.listFiles();
			System.out.println(files.toString());
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getName());
				if (files[i].getName().equals(".") || files[i].getName().equals("..")) continue;
				File localFile = new File("e:\\download\\" + datestr + "\\" + files[i].getName());// 设置本地下载的目录
				File fileparent = localFile.getParentFile();// 本地下载目录下的文件夹，如果不存在则创建
				if (!fileparent.exists()) {
					fileparent.mkdirs();
				}
				os = new FileOutputStream(localFile);
				ftpClient.retrieveFile(files[i].getName(), os);// 下载文件到本地

			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (ftpClient != null) {
					ftpClient.logout();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
