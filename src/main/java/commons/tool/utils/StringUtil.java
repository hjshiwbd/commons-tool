package commons.tool.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * StringUtil User: Administrator Date: 14-3-12 Time: 下午9:58
 */
public class StringUtil
{
	/**
	 * string encoding by: new String(s.getBytes("iso-8859-1"), "utf-8");
	 * 
	 * @param str
	 *            str to be encoding
	 * @return result
	 */
	public static String getUTF8String(String str)
	{
		if (str == null)
		{
			return null;
		}
		if (str.equals(""))
		{
			return "";
		}

		try
		{
			return new String(str.getBytes("iso-8859-1"), "utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new IllegalArgumentException("unknown encoding");
		}
	}

	/**
	 * null->""
	 * 
	 * @param str
	 * @return
	 * @author hjin
	 * @cratedate 2013-8-29 下午3:47:34
	 */
	public static String null2Empty(String str)
	{
		return StringUtils.isEmpty(str) ? "" : str;
	}

	public static String getShortStr(String str, int length)
	{
		if (str == null || str.equals(""))
		{
			return "";
		}

		if (str.length() < length)
		{
			return str;
		}

		return StringUtils.substring(str, 0, length) + "...";
	}

	/**
	 * 获取指定长度的随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length)
	{
		String result = "";
		String[] arr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Random r = new Random();
		for (int i = 0; i < length; i++)
		{
			result += arr[r.nextInt(arr.length)];
		}

		return result;
	}

	public static String getRandomString(int length)
	{
		String s = "qwertyupsadfghjklxzcvbnmQWERTYUPASDFGHJKLZXCVBNM23456789_-";
		StringBuffer buffer = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < length; i++)
		{
			int i2 = r.nextInt(s.length());
			buffer.append(s.charAt(i2));
		}
		return buffer.toString();
	}

	/**
	 * 数字左侧补零
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addZeroLeft(String str, int length)
	{
		if (str == null)
		{
			str = "";
		}
		String zeros = "";
		for (int i = 0; i < length - str.length(); i++)
		{
			zeros += "0";
		}

		return zeros + str;
	}

	/**
	 * obj->string(null->"")
	 * 
	 * @param obj
	 * @return
	 */
	public static String object2String(Object obj)
	{
		String result = "";
		if (obj == null)
		{
			result = "";
		}
		else if (obj instanceof Date)
		{
			result = DateUtil.formatDate((Date) obj);
		}
		else if (obj instanceof Integer)
		{
			result = String.valueOf(obj);
		}
		else if (obj instanceof String)
		{
			result = obj.toString();
		}
		else
		{
			result = obj.toString();
		}
		return result;
	}

}
