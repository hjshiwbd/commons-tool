package org.shinomin.commons.web.util;

import org.shinomin.commons.web.bean.SystemConfigBean;
import org.shinomin.commons.web.cache.impl.ContextInitCache;

public class CommonsfunctionCacheUtil
{
	public static final String DB_DRIVER = "database.config.driver";
	public static final String DB_URL = "database.config.url";
	public static final String DB_USERNAME = "database.config.username";
	public static final String DB_PASSWORD = "database.config.password";

	public static SystemConfigBean getSystemConfigBean(String key)
	{
		return (SystemConfigBean) ContextInitCache.getInstance().getCachedData(key);
	}

	public static String getSystemConfigValue(String key)
	{
		if (getSystemConfigBean(key) != null)
		{
			return (String) getSystemConfigBean(key).getConfvalue();
		}
		else
		{
			throw new IllegalArgumentException("systemconfig is not exists:" + key);
		}
	}

	public static String getString(String key)
	{
		return (String) ContextInitCache.getInstance().getCachedData(key);
	}

	public static String getDriver()
	{
		return getString(DB_DRIVER);
	}

	public static String getUrl()
	{
		return getString(DB_URL);
	}

	public static String getUsername()
	{
		return getString(DB_USERNAME);
	}

	public static String getPassword()
	{
		return getString(DB_PASSWORD);
	}

}
