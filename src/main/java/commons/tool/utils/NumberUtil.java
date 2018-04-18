package commons.tool.utils;

import java.text.DecimalFormat;

public class NumberUtil
{
	/**
	 * 数字格式化
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static Double format(Double d, String pattern)
	{
		DecimalFormat format = new DecimalFormat(pattern);
		return Double.valueOf(format.format(d));
	}
}
