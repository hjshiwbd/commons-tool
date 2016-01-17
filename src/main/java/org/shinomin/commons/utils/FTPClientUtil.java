package org.shinomin.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ftp工具类
 * 
 * @author hjin
 * @cratedate 2013-4-18 上午11:13:32
 * 
 */
public class FTPClientUtil
{
	private static Logger logger = LoggerFactory.getLogger(FTPClientUtil.class);

	/**
	 * ftp单文件上传
	 * 
	 * @author hjin
	 * @cratedate 2013-4-18 上午11:13:41
	 * @param serverIP
	 *            ftp服务器ip
	 * @param username
	 *            ftp登录名
	 * @param password
	 *            ftp登录密码
	 * @param uploadPath
	 *            服务器上传路径
	 * @param upload
	 *            上传文件
	 * 
	 */
	public static boolean uploadFile(String serverIP, String username,
	        String password, String uploadPath, File upload)
	{
		long time1 = System.currentTimeMillis();
		
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try
		{
			logger.info("ip=" + serverIP + ",uploadPath=" + uploadPath + "/"
			        + upload.getName());

			// 连接
			ftp.connect(serverIP);
			// 登录
			ftp.login(username, password);
			// 打开通道
			ftp.enterLocalPassiveMode();
			// 改变目录至上传路径
			ftp.changeWorkingDirectory(uploadPath);

			FTPFile[] files = ftp.listFiles(upload.getName());
			for (FTPFile file : files)
			{
				// logger.info(file.getName() + "----" + upload.getName());
				// 上传文件,若文件已存在,则删除
				if (file.getName().equals(upload.getName()))
				{
					ftp.deleteFile(uploadPath + file.getName());
					logger.info("delete exists file:" + uploadPath + "/"
					        + file.getName());
					break;
				}
			}

			// 服务器上传文件名
			String remoteFilename = upload.getName();
			// 本地文件流
			InputStream stream = new FileInputStream(upload);
			// 文件格式
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setBufferSize(1024);
			// 编码格式
			ftp.setControlEncoding("GBK");

			// 上传
			ftp.enterLocalPassiveMode();
			ftp.storeFile(remoteFilename, stream);
			// 关闭本地流
			stream.close();
			// 测试连接是否仍然保持
			ftp.noop();
			// 登出
			ftp.logout();
			// 断开
			ftp.disconnect();
			result = true;
			long time2 = System.currentTimeMillis();
			logger.info("ftpUpload end,time userd:" + (time2 - time1));
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ftp != null && ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 删除ftp上的文件
	 * 
	 * @author hjin
	 * @cratedate 2013-5-6 下午1:45:08
	 * @param serverIP
	 * @param username
	 * @param password
	 * @param filePath
	 * @param fileName
	 * @return
	 * 
	 */
	public static boolean deleteFile(String serverIP, String username,
	        String password, String filePath, String fileName)
	{
		long time1 = System.currentTimeMillis();
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try
		{
			logger.info("ip=" + serverIP + ",filePath=" + filePath + "/"
			        + fileName);

			// 连接
			ftp.connect(serverIP);
			// 登录
			ftp.login(username, password);
			// 改变目录至上传路径
			ftp.changeWorkingDirectory(filePath);

			FTPFile[] files = ftp.listFiles(fileName);
			for (FTPFile file : files)
			{
				// logger.info(file.getName() + "----" + upload.getName());
				// 上传文件,若文件已存在,则删除
				if (file.getName().equals(fileName))
				{
					ftp.deleteFile(filePath + fileName);
				}
			}
			// 测试连接是否仍然保持
			ftp.noop();
			// 登出
			ftp.logout();
			// 断开
			ftp.disconnect();
			result = true;
			long time2 = System.currentTimeMillis();
			logger.info("ftp delete end,time userd:" + (time2 - time1));
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ftp != null && ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
