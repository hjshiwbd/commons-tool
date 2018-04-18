package commons.tool.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commons.tool.utils.regex.IRegexReplaceRule;
import commons.tool.utils.regex.SimpleStringReplacementImpl;

public class RegexUtil
{
	/**
	 * 形如&lttag attribute="value"&gt,截取value;如有多个匹配,取第一个
	 * 
	 * @param src
	 * @param attribute
	 * @return
	 */
	public static String getHtmlTagValue(String src, String attribute)
	{
		String regex = attribute + "=\"[\\s\\S]*?\"";
		String tag = substringByRegex(src, regex).get(0);
		return tag.replace(attribute + "=\"", "").replace("\"", "");
	}

	/**
	 * 截取src中所有满足regex格式的文本
	 * 
	 * @param src
	 * @param regex
	 * @return
	 */
	public static List<String> substringByRegex(String src, String regex)
	{
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(src);
		while (matcher.find())
		{
			String group = matcher.group();
			list.add(group);
		}

		return list;
	}

	/**
	 * 截取src中所有满足regex格式的文本
	 * 
	 * @param src
	 *            原字符串
	 * @param regex
	 *            正则
	 * @param replacement
	 *            替换规则,需要实现接口IRegexReplaceRule
	 * @return list[index=0:替换了占位符后的src;其他:满足regex格式的所有字符串]
	 */
	public static List<String> replaceByRegex(String src, String regex,
	        IRegexReplaceRule replacement)
	{
		List<String> list = new ArrayList<String>();
		list.add(src);// 占用0位
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(src);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find())
		{
			String group = matcher.group();
			list.add(group);
			if (replacement != null)
			{
				matcher.appendReplacement(buffer,
				        replacement.getReplacement(group));
			}
		}
		matcher.appendTail(buffer);
		list.set(0, buffer.toString());// 更新0位
		return list;
	}

	/**
	 * 截取src中所有满足regex格式的文本
	 * 
	 * @param src
	 *            原字符串
	 * @param regex
	 *            正则
	 * @param replacement
	 *            替换值,=null不替换
	 * @return list[index=0:替换了占位符后的src;其他:满足regex格式的所有字符串]
	 */
	public static List<String> replaceByRegex(String src, String regex,
	        String replacement)
	{
		return replaceByRegex(src, regex, new SimpleStringReplacementImpl(
		        replacement));
	}

}
