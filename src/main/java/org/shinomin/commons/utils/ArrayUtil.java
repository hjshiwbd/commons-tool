package org.shinomin.commons.utils;

public class ArrayUtil
{
	/**
	 * js的Array.join()的实现
	 * 
	 * @param objs
	 * @param split
	 * @return
	 */
	public static String join(Object[] objs, String split)
	{
		if (objs == null || objs.length == 0 || split == null)
		{
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < objs.length; i++)
		{
			buffer.append(split + objs[i]);
		}

		return buffer.toString().substring(1);
	}

}
