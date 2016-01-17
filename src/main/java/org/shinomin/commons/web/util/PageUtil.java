package org.shinomin.commons.web.util;

import java.util.Map;
import java.util.Map.Entry;

import org.shinomin.commons.db.mybatis.Pager;
import org.shinomin.commons.utils.JsonUtil;

public class PageUtil
{

	public static <T> Pager<T> createPager(Integer curtPage, Integer countPerPage)
	{
		Pager<T> pager = new Pager<T>();
		curtPage = curtPage == null ? 1 : curtPage;
		pager.setCurtPage(curtPage);
		pager.setCountPerPage(countPerPage);
		return pager;
	}

	/**
	 * 把java对象传递到JSP中,并生成json对象.对象名为map.key
	 * 
	 * @param objs
	 * @return
	 */
	public static String create_SCRIPT_PARSE_JSON(Map<String, Object> map)
	{
		if (map == null)
		{
			return "";
		}

		StringBuffer buffer = new StringBuffer("<script type=\"text/javascript\">");

		String s1 = "var {name}=eval({value});";
		String s2 = "var {name}='{value}';";

		for (Entry<String, Object> e : map.entrySet())
		{
			String key = e.getKey();
			Object o = e.getValue();
			if (o == null)
			{
				continue;
			}

			if (o instanceof String)
			{
				String tmpStr = s2.replace("{name}", key).replace("{value}", o.toString());
				buffer.append(tmpStr);
			}
			else
			{
				String tmpStr = s1.replace("{name}", key).replace("{value}", JsonUtil.toJson(o));
				buffer.append(tmpStr);
			}
		}

		buffer.append("</script>");

		return buffer.toString();
	}
}
