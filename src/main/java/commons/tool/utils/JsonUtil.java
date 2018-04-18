package commons.tool.utils;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * json通用方法
 *
 */
public class JsonUtil {
	/**
	 * obj转json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * obj转json字符串,格式美化
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonFull(Object obj) {
		return JSON.toJSONString(obj, true);
	}

	/**
	 * json字符串转obj对象
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T toObj(String json, Class<T> clz) {
		if (json == null) {
			throw new RuntimeException("json is null");
		}
		if (json.trim().startsWith("{")) {
			return JSON.parseObject(json, clz);
		}
		throw new RuntimeException("err json format");
	}

	/**
	 * json字符串转map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<?, ?> toObjMap(String json) {
		return toObj(json, Map.class);
	}

	/**
	 * json字符串转list对象
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> List<T> toObjList(String json, Class<T> clz) {
		if (json == null) {
			throw new RuntimeException("json is null");
		}
		if (json.trim().startsWith("[")) {
			return JSON.parseArray(json, clz);
		}
		throw new RuntimeException("err json format");
	}

}
