package commons.tool.utils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class ConvertUtil
{
	public static String objToString(Object obj)
	{
		if (obj == null)
		{
			return "";
		}
		return obj.toString();
	}

	/**
	 * 根据key反射value到obj中
	 * 
	 * @param map
	 * @param cls
	 * @return
	 * @author hjin
	 */
	@SuppressWarnings("unchecked")
	public static <T> T mapToObject(Map<String, Object> map, Class<T> cls)
	{
		if (map == null)
		{
			return null;
		}

		T obj = (T) ReflectUtil.newInstance(cls.getName());

		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<String, Object> en = it.next();
			String key = en.getKey();
			Object o = en.getValue();
			o = parseObj(cls, key, o);

			ReflectUtil.invokeSetMethod(obj, key, o);
		}

		return obj;
	}

	/**
	 * 当obj类型不是string时的处理
	 * 
	 * @param cls
	 * @param key
	 * @param o
	 * @return
	 */
	private static <T> Object parseObj(Class<T> cls, String key, Object o)
	{
		Method method = ReflectUtil.findMethodByName(cls,
		        ReflectUtil.getGetMethodName(key));
		if (method==null)
        {
	        return null;
        }

		Class<?> cc = method.getReturnType();
		if (cc.equals(Integer.class))
		{
			return Integer.parseInt(o.toString());
		}
		else
		{
			return o;
		}
	}
	
}
