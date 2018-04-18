package commons.tool.utils;

public class LogUtil1
{
	public static int getLineNumber()
	{
		return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}

	public static String getFileName()
	{
		return Thread.currentThread().getStackTrace()[2].getFileName();
	}

	public static void main(String args[])
	{
		System.out.println("[" + getFileName() + "：" + getLineNumber() + "]"
		        + "Hello World!");
	}
}
