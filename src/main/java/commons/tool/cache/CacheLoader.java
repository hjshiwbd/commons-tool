package commons.tool.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.tool.cache.impl.PublicCache;

public class CacheLoader
{
	private static Logger logger = LoggerFactory.getLogger(CacheLoader.class
	        .getName());

	public static String loadString(String key)
	{
		String s = (String) PublicCache.getInstance().getCachedData(key);
		return s;
	}

	public static Integer loadInt(String key)
	{
		String s = (String) PublicCache.getInstance().getCachedData(key);
		String regex = "\\d+";
		if (s.matches(regex))
		{
			return Integer.parseInt(s);
		}
		else
		{
			logger.error("unkown int format for {}", key);
			return -1;
		}
	}
}
