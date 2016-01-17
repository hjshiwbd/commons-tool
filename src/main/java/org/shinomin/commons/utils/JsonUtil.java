package org.shinomin.commons.utils;

import java.util.Map;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

public class JsonUtil {
    public static String toJson(Object obj) {
        return Json.toJson(obj, JsonFormat.compact());
    }

    public static String toJsonFull(Object obj) {
        return Json.toJson(obj, JsonFormat.tidy());
    }

    public static Object toObj(String json) {
        if (json.startsWith("[")) {
            return Json.fromJsonAsList(Map.class, json);
        } else {
            return Json.fromJson(Map.class, json);
        }
    }

}
