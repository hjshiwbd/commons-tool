package commons.tool.utils;

public class WildcardUtil
{
	/**
	 * 通配符匹配. eg: isWildMatch("abc*x*yz*", "abc123xxxyz") = true
	 * 
	 * @param wildcard
	 * @param str
	 * @return
	 */
	public static boolean isWildMatch(String wildcard, String str)
	{
		// 把通配符转成正则
		String regex = parseWildcard(wildcard);

		// 正则匹配str
		return str.matches(regex);
	}

	private static String parseWildcard(String wildcard)
	{
		// 特殊字符转译
		String result = "^";
		for (int i = 0; i < wildcard.length(); i++)
		{
			char ch = wildcard.charAt(i);
			if (ch == '*')
			{
				result += ".*";
			}
			else if (ch == '?')
			{
				result += ".{1}";
			}
			else if (isSpecial(ch))
			{
				result += "\\" + ch;
			}
			else
			{
				result += ch;
			}
		}
		result += "$";
		return result;
	}

	private static boolean isSpecial(char ch)
	{
		char regexChar[] = { '$', '^', '[', ']', '(', ')', '{', '|', '+', '.',
		        '\\' };
		for (int j = 0; j < regexChar.length; j++)
		{
			if (ch == regexChar[j])
			{
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args)
	{
		System.out.println(isWildMatch("abc*x*yz*", "abc123xxxyz"));
	}
}
