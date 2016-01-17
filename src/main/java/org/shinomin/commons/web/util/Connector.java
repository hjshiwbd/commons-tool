package org.shinomin.commons.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connector
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String driver;
	private String url;
	private String username;
	private String password;

	public Connection getConn()
	{
		checkConfig(driver, url, username, password);

		Connection conn = null;
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (Exception e)
		{
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}

	private void checkConfig(String driver, String url, String username, String password)
	{
		logger.debug("driver:{}, url:{}, username:{}, password:{}", driver, url, username, password);
		if (isBlank(driver) || isBlank(url) || isBlank(username) || isBlank(password))
		{
			throw new IllegalArgumentException(String.format("no such propery found in properites file: " + "%s, %s, %s, %s", CommonsfunctionCacheUtil.DB_URL,
			        CommonsfunctionCacheUtil.DB_URL, CommonsfunctionCacheUtil.DB_URL, CommonsfunctionCacheUtil.DB_URL));
		}
	}

	private boolean isBlank(String str)
	{
		return str == null || "".equals(str);
	}

	public void closeConn(Connection conn, Statement ps, ResultSet rs)
	{
		try
		{
			if (rs != null && !rs.isClosed())
			{
				rs.close();
			}
			if (ps != null && !ps.isClosed())
			{
				ps.close();
			}
			if (conn != null && !conn.isClosed())
			{
				conn.close();
			}
		}
		catch (SQLException e)
		{
			logger.warn(e.getMessage());
		}
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
